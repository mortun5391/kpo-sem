package com.antiplagiarism.analysis.service.impl;

import com.antiplagiarism.analysis.dto.ReportDTO;
import com.antiplagiarism.analysis.entity.ReportEntity;
import com.antiplagiarism.analysis.exception.AnalysisException;
import com.antiplagiarism.analysis.repository.ReportRepository;
import com.antiplagiarism.analysis.service.ReportService;
import com.antiplagiarism.shared.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Cacheable(value = "reports", key = "#id")
    public ReportDTO getReportById(Long id) {
        log.info("Fetching report from database: id={}", id);
        ReportEntity report = reportRepository.findById(id)
                .orElseThrow(() -> new AnalysisException("Report not found with id: " + id));

        return convertToDTO(report);
    }

    @Override
    @Cacheable(value = "workReports", key = "#workId")
    public List<ReportDTO> getReportsByWorkId(Long workId) {
        log.info("Fetching reports for work from database: workId={}", workId);
        return reportRepository.findByWorkId(workId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getLatestReportByWorkId(Long workId) {
        log.info("Fetching latest report for work: workId={}", workId);
        ReportEntity report = reportRepository.findTopByWorkIdOrderByCreatedAtDesc(workId)
                .orElseThrow(() -> new AnalysisException("No reports found for workId: " + workId));

        return convertToDTO(report);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"reports", "workReports"}, allEntries = true)
    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new AnalysisException("Report not found with id: " + id);
        }

        reportRepository.deleteById(id);

        // Инвалидируем кэш для конкретного отчета
        String cacheKey = ApiConstants.CACHE_REPORT_PREFIX + id;
        redisTemplate.delete(cacheKey);

        log.info("Report deleted: id={}", id);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"reports", "workReports"}, allEntries = true)
    public ReportDTO updateReport(Long id, ReportDTO reportDTO) {
        ReportEntity report = reportRepository.findById(id)
                .orElseThrow(() -> new AnalysisException("Report not found with id: " + id));

        // Обновляем поля отчета
        report.setStatus(reportDTO.getStatus());
        report.setSimilarityScore(reportDTO.getSimilarityScore());
        report.setPlagiarized(reportDTO.isPlagiarized());
        report.setAnalysisDetails(reportDTO.getAnalysisDetails());
        report.setErrorMessage(reportDTO.getErrorMessage());

        ReportEntity updatedReport = reportRepository.save(report);

        // Инвалидируем кэш для конкретного отчета
        String cacheKey = ApiConstants.CACHE_REPORT_PREFIX + id;
        redisTemplate.delete(cacheKey);

        log.info("Report updated: id={}", id);
        return convertToDTO(updatedReport);
    }

    private ReportDTO convertToDTO(ReportEntity entity) {
        return ReportDTO.builder()
                .id(entity.getId())
                .workId(entity.getWorkId())
                .status(entity.getStatus())
                .similarityScore(entity.getSimilarityScore())
                .plagiarized(entity.isPlagiarized())
                .analysisDetails(entity.getAnalysisDetails())
                .errorMessage(entity.getErrorMessage())
                .startedAt(entity.getStartedAt())
                .completedAt(entity.getCompletedAt())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}