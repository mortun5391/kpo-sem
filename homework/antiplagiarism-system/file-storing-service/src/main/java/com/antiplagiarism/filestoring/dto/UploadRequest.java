package com.antiplagiarism.filestoring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {

    @NotNull(message = "File is required")
    private MultipartFile file;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Assignment ID is required")
    private String assignmentId;
}