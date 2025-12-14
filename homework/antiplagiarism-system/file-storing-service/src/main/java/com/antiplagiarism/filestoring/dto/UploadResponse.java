package com.antiplagiarism.filestoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
    private Long workId;
    private String fileName;
    private String storedFileName;
    private Long fileSize;
    private LocalDateTime uploadDate;
    private String downloadUri;
}