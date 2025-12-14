package com.antiplagiarism.analysis.service;

import java.util.List;

public interface PlagiarismDetectionService {
    double detectPlagiarism(Long workId, String text);
    double calculateSimilarity(String text1, String text2);
    List<String> generateShingles(String text, int shingleSize);
    String preprocessText(String text);
}