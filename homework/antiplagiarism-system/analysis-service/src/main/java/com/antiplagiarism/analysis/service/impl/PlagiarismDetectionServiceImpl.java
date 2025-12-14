package com.antiplagiarism.analysis.service.impl;

import com.antiplagiarism.analysis.entity.ReportEntity;
import com.antiplagiarism.analysis.repository.ReportRepository;
import com.antiplagiarism.analysis.service.PlagiarismDetectionService;
import com.antiplagiarism.shared.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlagiarismDetectionServiceImpl implements PlagiarismDetectionService {

    private final ReportRepository reportRepository;

    @Override
    public double detectPlagiarism(Long workId, String text) {
        log.info("Detecting plagiarism for workId: {}", workId);

        // Препроцессинг текста
        String processedText = preprocessText(text);

        // Генерация шинглов для текущего текста
        Set<String> currentShingles = new HashSet<>(generateShingles(processedText, ApiConstants.SHINGLE_SIZE));

        if (currentShingles.isEmpty()) {
            log.warn("No shingles generated for workId: {}", workId);
            return 0.0;
        }

        // Получаем все предыдущие работы для сравнения
        List<ReportEntity> previousReports = reportRepository.findCompletedReportsExcludingWork(workId);

        double maxSimilarity = 0.0;

        for (ReportEntity report : previousReports) {
            if (report.getAnalysisDetails() != null && report.getAnalysisDetails().containsKey("textShingles")) {
                @SuppressWarnings("unchecked")
                Set<String> previousShingles = new HashSet<>((Collection<String>)
                        report.getAnalysisDetails().get("textShingles"));

                double similarity = calculateJaccardSimilarity(currentShingles, previousShingles);
                maxSimilarity = Math.max(maxSimilarity, similarity);

                log.debug("Similarity with report {}: {}", report.getId(), similarity);
            }
        }

        log.info("Max similarity for workId {}: {}", workId, maxSimilarity);
        return maxSimilarity;
    }

    @Override
    public double calculateSimilarity(String text1, String text2) {
        String processedText1 = preprocessText(text1);
        String processedText2 = preprocessText(text2);

        Set<String> shingles1 = new HashSet<>(generateShingles(processedText1, ApiConstants.SHINGLE_SIZE));
        Set<String> shingles2 = new HashSet<>(generateShingles(processedText2, ApiConstants.SHINGLE_SIZE));

        return calculateJaccardSimilarity(shingles1, shingles2);
    }

    @Override
    public List<String> generateShingles(String text, int shingleSize) {
        List<String> shingles = new ArrayList<>();

        if (text == null || text.trim().isEmpty()) {
            return shingles;
        }

        String[] words = text.split("\\s+");

        if (words.length < shingleSize) {
            // Если слов меньше, чем размер шингла, используем весь текст как один шингл
            shingles.add(text);
            return shingles;
        }

        for (int i = 0; i <= words.length - shingleSize; i++) {
            StringBuilder shingle = new StringBuilder();
            for (int j = 0; j < shingleSize; j++) {
                shingle.append(words[i + j]);
                if (j < shingleSize - 1) {
                    shingle.append(" ");
                }
            }
            shingles.add(shingle.toString());
        }

        return shingles;
    }

    @Override
    public String preprocessText(String text) {
        if (text == null) {
            return "";
        }

        // Приведение к нижнему регистру
        String processed = text.toLowerCase();

        // Удаление пунктуации
        processed = processed.replaceAll("[^a-zA-Zа-яА-Я0-9\\s]", " ");

        // Удаление лишних пробелов
        processed = processed.replaceAll("\\s+", " ").trim();

        // Удаление стоп-слов (упрощенный список)
        processed = removeStopWords(processed);

        return processed;
    }

    private double calculateJaccardSimilarity(Set<String> set1, Set<String> set2) {
        if (set1.isEmpty() || set2.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        if (union.isEmpty()) {
            return 0.0;
        }

        return (double) intersection.size() / union.size();
    }

    private String removeStopWords(String text) {
        // Упрощенный список стоп-слов
        Set<String> stopWords = new HashSet<>(Arrays.asList(
                "и", "в", "во", "не", "что", "он", "на", "я", "с", "со", "как", "а", "то", "все",
                "она", "так", "его", "но", "да", "ты", "к", "у", "же", "вы", "за", "бы", "по",
                "только", "ее", "мне", "было", "вот", "от", "меня", "еще", "нет", "о", "из", "ему",
                "теперь", "когда", "даже", "ну", "вдруг", "ли", "если", "уже", "или", "ни", "быть",
                "был", "него", "до", "вас", "нибудь", "опять", "уж", "вам", "ведь", "там", "потом",
                "себя", "ничего", "ей", "может", "они", "тут", "где", "есть", "надо", "ней", "для",
                "мы", "тебя", "их", "чем", "была", "сам", "чтоб", "без", "будто", "чего", "раз",
                "тоже", "себе", "под", "будет", "ж", "тогда", "кто", "этот", "того", "потому",
                "этого", "какой", "совсем", "ним", "здесь", "этом", "один", "почти", "мой", "тем",
                "чтобы", "нее", "сейчас", "были", "куда", "зачем", "всех", "никогда", "можно",
                "при", "наконец", "два", "об", "другой", "хоть", "после", "над", "больше", "тот",
                "через", "эти", "нас", "про", "всего", "них", "какая", "много", "разве", "три",
                "эту", "моя", "впрочем", "хорошо", "свою", "этой", "перед", "иногда", "лучше",
                "чуть", "том", "нельзя", "такой", "им", "более", "всегда", "конечно", "всю", "между",
                "the", "and", "a", "an", "in", "on", "at", "to", "for", "of", "with", "by", "from",
                "as", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had",
                "do", "does", "did", "will", "would", "should", "could", "can", "may", "might",
                "must", "shall", "this", "that", "these", "those", "i", "you", "he", "she", "it",
                "we", "they", "me", "him", "her", "us", "them", "my", "your", "his", "its", "our",
                "their", "mine", "yours", "hers", "ours", "theirs"
        ));

        return Arrays.stream(text.split("\\s+"))
                .filter(word -> !stopWords.contains(word))
                .collect(Collectors.joining(" "));
    }
}