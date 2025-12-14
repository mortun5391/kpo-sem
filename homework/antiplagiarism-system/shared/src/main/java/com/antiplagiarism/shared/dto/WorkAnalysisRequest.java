package com.antiplagiarism.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на анализ работы")
public class WorkAnalysisRequest {

    @NotNull(message = "Work ID is required")
    @Schema(description = "ID работы", example = "1")
    private Long workId;

    @NotBlank(message = "Student ID is required")
    @Schema(description = "ID студента", example = "student123")
    private String studentId;

    @NotBlank(message = "Assignment ID is required")
    @Schema(description = "ID задания", example = "assignment-1")
    private String assignmentId;
}