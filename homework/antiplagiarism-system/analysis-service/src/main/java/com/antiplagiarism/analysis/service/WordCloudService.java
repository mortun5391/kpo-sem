package com.antiplagiarism.analysis.service;

import com.antiplagiarism.analysis.dto.WordCloudRequest;

public interface WordCloudService {
    String generateWordCloud(WordCloudRequest request);
    String getWordCloud(Long workId);
    void deleteWordCloud(Long workId);
}