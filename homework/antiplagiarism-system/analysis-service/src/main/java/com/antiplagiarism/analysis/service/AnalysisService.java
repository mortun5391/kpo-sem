package com.antiplagiarism.analysis.service;

import com.antiplagiarism.analysis.dto.AnalysisResponse;

public interface AnalysisService {
    AnalysisResponse startAnalysis(Long workId);
    String getAnalysisStatus(Long workId);
    void cancelAnalysis(Long workId);
    void processAnalysis(Long workId);
}