package com.antiplagiarism.analysis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordCloudRequest {

    @NotNull(message = "Work ID is required")
    private Long workId;

    private int width;
    private int height;
    private List<String> colors;
    private String backgroundColor;
    private String fontFamily;
    private int minFontSize;
    private int maxFontSize;

    @Builder.Default
    private boolean forceRegenerate = false;
}