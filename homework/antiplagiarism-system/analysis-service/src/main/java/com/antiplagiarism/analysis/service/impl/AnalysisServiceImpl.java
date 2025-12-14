package com.antiplagiarism.analysis.service.impl;

import org.apache.pdfbox.Loader;

import com.antiplagiarism.analysis.dto.AnalysisResponse;
import com.antiplagiarism.analysis.entity.ReportEntity;
import com.antiplagiarism.analysis.event.AnalysisEventPublisher;
import com.antiplagiarism.analysis.repository.ReportRepository;
import com.antiplagiarism.analysis.service.AnalysisService;
import com.antiplagiarism.analysis.service.PlagiarismDetectionService;
import com.antiplagiarism.analysis.service.ReportService;
import com.antiplagiarism.shared.constants.ApiConstants;
import com.antiplagiarism.shared.dto.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisServiceImpl implements AnalysisService {

    private final ReportRepository reportRepository;
    private final PlagiarismDetectionService plagiarismDetectionService;
    private final ReportService reportService;
    private final AnalysisEventPublisher eventPublisher;
    private final WebClient webClient;

    @Value("${file.service.url:http://file-storing-service:8081}")
    private String fileServiceUrl;

    @Override
    @Transactional
    public AnalysisResponse startAnalysis(Long workId) {
        log.info("Starting analysis for workId: {}", workId);

        // Проверяем, не выполняется ли уже анализ
        if (reportRepository.existsByWorkIdAndStatus(workId, "IN_PROGRESS")) {
            throw new IllegalStateException("Analysis already in progress for workId: " + workId);
        }

        // Создаем запись об анализе
        ReportEntity report = ReportEntity.builder()
                .workId(workId)
                .status("IN_PROGRESS")
                .startedAt(LocalDateTime.now())
                .build();

        ReportEntity savedReport = reportRepository.save(report);

        // Отправляем событие о начале анализа
        eventPublisher.publishAnalysisEvent(
                EventType.ANALYSIS_STARTED,
                workId,
                "analysis-service",
                Map.of("reportId", savedReport.getId())
        );

        // Запускаем асинхронную обработку
        processAnalysisAsync(workId, savedReport.getId());

        return AnalysisResponse.builder()
                .reportId(savedReport.getId())
                .workId(workId)
                .status("IN_PROGRESS")
                .message("Analysis started successfully")
                .startedAt(savedReport.getStartedAt())
                .build();
    }

    @Async
    public void processAnalysisAsync(Long workId, Long reportId) {
        try {
            processAnalysisInternal(workId, reportId);
        } catch (Exception e) {
            log.error("Error during analysis for workId: {}, reportId: {}", workId, reportId, e);

            // Обновляем статус отчета на FAILED
            ReportEntity report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));

            report.setStatus("FAILED");
            report.setErrorMessage(e.getMessage());
            report.setCompletedAt(LocalDateTime.now());
            reportRepository.save(report);

            // Отправляем событие об ошибке
            eventPublisher.publishErrorEvent(
                    EventType.ERROR_OCCURRED,
                    workId,
                    "analysis-service",
                    "Analysis failed: " + e.getMessage(),
                    "ANALYSIS_ERROR"
            );
        }
    }

    @Override
    @Transactional
    public void processAnalysis(Long workId) {
        log.info("Processing analysis for workId: {}", workId);

        // Находим активный отчет для этой работы
        ReportEntity report = reportRepository.findTopByWorkIdAndStatus(workId, "IN_PROGRESS")
                .orElseThrow(() -> new RuntimeException("No in-progress analysis found for workId: " + workId));

        // Вызываем внутренний метод с двумя параметрами
        processAnalysisInternal(workId, report.getId());
    }

    // Внутренний метод с двумя параметрами
    @Transactional
    private void processAnalysisInternal(Long workId, Long reportId) {
        log.info("Processing analysis for workId: {}, reportId: {}", workId, reportId);

        ReportEntity report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));

        try {
            // Получаем файл из file-storing-service
            String fileContent = getFileContent(workId);

            if (fileContent == null || fileContent.trim().isEmpty()) {
                throw new IllegalArgumentException("File content is empty for workId: " + workId);
            }

            // Проверяем минимальную длину текста
            if (fileContent.length() < ApiConstants.MIN_TEXT_LENGTH) {
                throw new IllegalArgumentException(
                        String.format("Text is too short. Minimum length: %d, actual: %d",
                                ApiConstants.MIN_TEXT_LENGTH, fileContent.length()));
            }

            // Выполняем анализ на плагиат
            double similarityScore = plagiarismDetectionService.detectPlagiarism(workId, fileContent);
            boolean isPlagiarized = similarityScore >= ApiConstants.PLAGIARISM_THRESHOLD;

            // Обновляем отчет
            report.setStatus("COMPLETED");
            report.setSimilarityScore(similarityScore);
            report.setPlagiarized(isPlagiarized);
            report.setCompletedAt(LocalDateTime.now());
            report.setAnalysisDetails(Map.of(
                    "similarityScore", similarityScore,
                    "threshold", ApiConstants.PLAGIARISM_THRESHOLD,
                    "isPlagiarized", isPlagiarized,
                    "shingleSize", ApiConstants.SHINGLE_SIZE
            ));

            reportRepository.save(report);

            log.info("Analysis completed for workId: {}, similarityScore: {}, isPlagiarized: {}",
                    workId, similarityScore, isPlagiarized);

            // Отправляем событие о завершении анализа
            eventPublisher.publishAnalysisEvent(
                    isPlagiarized ? EventType.PLAGIARISM_DETECTED : EventType.ANALYSIS_COMPLETED,
                    workId,
                    "analysis-service",
                    Map.of(
                            "reportId", reportId,
                            "similarityScore", similarityScore,
                            "isPlagiarized", isPlagiarized,
                            "threshold", ApiConstants.PLAGIARISM_THRESHOLD
                    )
            );

        } catch (Exception e) {
            log.error("Error processing analysis for workId: {}", workId, e);

            report.setStatus("FAILED");
            report.setErrorMessage(e.getMessage());
            report.setCompletedAt(LocalDateTime.now());
            reportRepository.save(report);

            throw e;
        }
    }

    @Override
    public String getAnalysisStatus(Long workId) {
        ReportEntity latestReport = reportRepository.findTopByWorkIdOrderByCreatedAtDesc(workId)
                .orElseThrow(() -> new RuntimeException("No reports found for workId: " + workId));

        return latestReport.getStatus();
    }

    @Override
    @Transactional
    public void cancelAnalysis(Long workId) {
        log.info("Cancelling analysis for workId: {}", workId);

        ReportEntity report = reportRepository.findTopByWorkIdAndStatus(workId, "IN_PROGRESS")
                .orElseThrow(() -> new RuntimeException("No in-progress analysis found for workId: " + workId));

        report.setStatus("CANCELLED");
        report.setCompletedAt(LocalDateTime.now());
        reportRepository.save(report);

        log.info("Analysis cancelled for workId: {}", workId);
    }

    private String getFileContent(Long workId) {
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
            log.error("Error getting file content for workId: {}", workId, e);
            throw new RuntimeException("Failed to get file content: " + e.getMessage(), e);
        }
    }

    private String extractTextFromFile(byte[] fileBytes, String mimeType) {
        // Реализация извлечения текста в зависимости от типа файла
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
        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return text;
        } catch (Exception e) {
            log.error("Error extracting text from PDF", e);
            throw new RuntimeException("Failed to extract text from PDF: " + e.getMessage(), e);
        }
    }

    private String extractTextFromDocx(byte[] docxBytes) {
        try {
            // Используем Apache POI для извлечения текста из DOCX
            XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(docxBytes));
            StringBuilder text = new StringBuilder();

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText()).append("\n");
            }

            document.close();
            return text.toString();
        } catch (Exception e) {
            log.error("Error extracting text from DOCX", e);
            throw new RuntimeException("Failed to extract text from DOCX: " + e.getMessage(), e);
        }
    }
}