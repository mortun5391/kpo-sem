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
public class WorkDTO {
    private Long id;
    private String studentId;
    private String assignmentId;
    private String originalFileName;
    private String storedFileName;
    private Long fileSize;
    private String mimeType;
    private LocalDateTime uploadDate;
    private String status;
}