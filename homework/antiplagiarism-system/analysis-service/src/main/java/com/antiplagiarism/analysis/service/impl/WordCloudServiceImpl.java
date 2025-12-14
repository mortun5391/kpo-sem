package com.antiplagiarism.analysis.service.impl;

import com.antiplagiarism.analysis.dto.WordCloudRequest;
import com.antiplagiarism.analysis.service.WordCloudService;
import com.fasterxml.jackson.databind.ObjectMapper; // Добавьте этот импорт
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader; // Добавьте этот импорт
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordCloudServiceImpl implements WordCloudService {

    private final RedisTemplate<String, String> redisTemplate;
    private final WebClient webClient;
    private final ObjectMapper objectMapper; // Добавьте ObjectMapper

    @Value("${file.service.url:http://file-storing-service:8081}")
    private String fileServiceUrl;

    @Value("${wordcloud.api.url:https://quickchart.io/wordcloud}")
    private String wordCloudApiUrl;

    @Value("${wordcloud.cache.ttl.minutes:60}")
    private long cacheTtlMinutes;

    @Override
    public String generateWordCloud(WordCloudRequest request) {
        Long workId = request.getWorkId();
        log.info("Generating word cloud for workId: {}", workId);

        // Проверяем кэш
        String cacheKey = "wordcloud:" + workId;
        String cachedUrl = redisTemplate.opsForValue().get(cacheKey);

        if (cachedUrl != null && !request.isForceRegenerate()) {
            log.info("Returning cached word cloud for workId: {}", workId);
            return cachedUrl;
        }

        try {
            // Получаем текст работы
            String text = getWorkText(workId);

            if (text == null || text.trim().isEmpty()) {
                throw new RuntimeException("Text content is empty for workId: " + workId);
            }

            // Анализируем текст и подсчитываем частоту слов
            Map<String, Integer> wordFrequencies = analyzeText(text);

            // Генерируем URL для облака слов
            String wordCloudUrl = generateWordCloudUrl(wordFrequencies, request);

            // Сохраняем в кэш
            redisTemplate.opsForValue().set(
                    cacheKey,
                    wordCloudUrl,
                    Duration.ofMinutes(cacheTtlMinutes)
            );

            log.info("Word cloud generated for workId: {}", workId);
            return wordCloudUrl;

        } catch (Exception e) {
            log.error("Error generating word cloud for workId: {}", workId, e);
            throw new RuntimeException("Failed to generate word cloud: " + e.getMessage(), e);
        }
    }

    @Override
    public String getWordCloud(Long workId) {
        String cacheKey = "wordcloud:" + workId;
        String wordCloudUrl = redisTemplate.opsForValue().get(cacheKey);

        if (wordCloudUrl == null) {
            throw new RuntimeException("Word cloud not found for workId: " + workId);
        }

        return wordCloudUrl;
    }

    @Override
    public void deleteWordCloud(Long workId) {
        String cacheKey = "wordcloud:" + workId;
        redisTemplate.delete(cacheKey);
        log.info("Word cloud deleted from cache for workId: {}", workId);
    }

    private String getWorkText(Long workId) {
        try {
            // Получаем метаданные работы
            Map<String, Object> workInfo = webClient.get()
                    .uri(fileServiceUrl + "/api/v1/works/{id}", workId)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (workInfo == null || !workInfo.containsKey("data")) {
                throw new RuntimeException("Work not found: " + workId);
            }

            // Скачиваем файл
            byte[] fileBytes = webClient.get()
                    .uri(fileServiceUrl + "/api/v1/works/{id}/file", workId)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();

            if (fileBytes == null) {
                throw new RuntimeException("File not found for workId: " + workId);
            }

            // Определяем тип файла и извлекаем текст
            String mimeType = (String) ((Map<?, ?>) workInfo.get("data")).get("mimeType");
            return extractTextFromFile(fileBytes, mimeType);

        } catch (Exception e) {
            log.error("Error getting work text for workId: {}", workId, e);
            throw new RuntimeException("Failed to get work text: " + e.getMessage(), e);
        }
    }

    private String extractTextFromFile(byte[] fileBytes, String mimeType) {
        // Та же реализация, что и в AnalysisServiceImpl
        if (mimeType == null) {
            return new String(fileBytes);
        }

        switch (mimeType) {
            case "text/plain":
                return new String(fileBytes);
            case "application/pdf":
                return extractTextFromPdf(fileBytes);
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return extractTextFromDocx(fileBytes);
            default:
                log.warn("Unsupported file type: {}, trying to extract as plain text", mimeType);
                return new String(fileBytes);
        }
    }

    private String extractTextFromPdf(byte[] pdfBytes) {
        // Реализация извлечения текста из PDF с использованием Loader
        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return text;
        } catch (Exception e) {
            log.error("Error extracting text from PDF", e);
            throw new RuntimeException("Failed to extract text from PDF", e);
        }
    }

    private String extractTextFromDocx(byte[] docxBytes) {
        // Реализация извлечения текста из DOCX
        try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(docxBytes))) {
            StringBuilder text = new StringBuilder();

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText()).append("\n");
            }

            return text.toString();
        } catch (Exception e) {
            log.error("Error extracting text from DOCX", e);
            throw new RuntimeException("Failed to extract text from DOCX", e);
        }
    }

    private Map<String, Integer> analyzeText(String text) {
        // Препроцессинг текста
        String processedText = preprocessText(text);

        // Разделяем на слова и подсчитываем частоту
        String[] words = processedText.split("\\s+");
        Map<String, Integer> frequencies = new HashMap<>();

        for (String word : words) {
            if (word.length() > 2) { // Игнорируем очень короткие слова
                frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
            }
        }

        // Сортируем по частоте и берем топ-N слов
        return frequencies.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(100) // Ограничиваем количество слов для облака
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private String preprocessText(String text) {
        if (text == null) {
            return "";
        }

        // Приведение к нижнему регистру
        String processed = text.toLowerCase();

        // Удаление пунктуации
        processed = processed.replaceAll("[^a-zA-Zа-яА-Я0-9\\s]", " ");

        // Удаление лишних пробелов
        processed = processed.replaceAll("\\s+", " ").trim();

        // Удаление стоп-слов
        processed = removeStopWords(processed);

        return processed;
    }

    private String removeStopWords(String text) {
        // Упрощенный список стоп-слов
        Set<String> stopWords = new HashSet<>(Arrays.asList(
                "и", "в", "во", "не", "что", "он", "на", "я", "с", "со", "как", "а", "то", "все",
                "она", "так", "его", "но", "да", "ты", "к", "у", "же", "вы", "за", "бы", "по",
                "the", "and", "a", "an", "in", "on", "at", "to", "for", "of", "with", "by"
        ));

        return Arrays.stream(text.split("\\s+"))
                .filter(word -> !stopWords.contains(word))
                .collect(Collectors.joining(" "));
    }

    private String generateWordCloudUrl(Map<String, Integer> wordFrequencies, WordCloudRequest request) {
        // Создаем параметры для API QuickChart
        Map<String, Object> chartConfig = new HashMap<>();
        chartConfig.put("type", "wordCloud");

        Map<String, Object> data = new HashMap<>();
        data.put("text", wordFrequencies.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(",")));
        chartConfig.put("data", data);

        Map<String, Object> options = new HashMap<>();

        // Настраиваем параметры облака слов из запроса или используем значения по умолчанию
        if (request.getWidth() > 0) {
            options.put("width", request.getWidth());
        } else {
            options.put("width", 800);
        }

        if (request.getHeight() > 0) {
            options.put("height", request.getHeight());
        } else {
            options.put("height", 400);
        }

        List<String> colors = request.getColors();
        if (colors != null && !colors.isEmpty()) {
            options.put("colors", colors);
        } else {
            options.put("colors", Arrays.asList("#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd"));
        }

        if (request.getBackgroundColor() != null) {
            options.put("backgroundColor", request.getBackgroundColor());
        }

        if (request.getFontFamily() != null) {
            options.put("fontFamily", request.getFontFamily());
        }

        if (request.getMinFontSize() > 0) {
            options.put("minFontSize", request.getMinFontSize());
        }

        if (request.getMaxFontSize() > 0) {
            options.put("maxFontSize", request.getMaxFontSize());
        }

        chartConfig.put("options", options);

        // Кодируем конфигурацию в URL
        try {
            String jsonConfig = objectMapper.writeValueAsString(chartConfig);
            String encodedConfig = Base64.getUrlEncoder().encodeToString(jsonConfig.getBytes());

            return wordCloudApiUrl + "?c=" + encodedConfig;

        } catch (Exception e) {
            log.error("Error generating word cloud URL", e);
            throw new RuntimeException("Failed to generate word cloud URL", e);
        }
    }
}