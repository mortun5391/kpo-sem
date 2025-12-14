package com.antiplagiarism.analysis.service;

import com.antiplagiarism.analysis.dto.ReportDTO;

import java.util.List;

public interface ReportService {
    ReportDTO getReportById(Long id);
    List<ReportDTO> getReportsByWorkId(Long workId);
    ReportDTO getLatestReportByWorkId(Long workId);
    void deleteReport(Long id);
    ReportDTO updateReport(Long id, ReportDTO reportDTO);
}