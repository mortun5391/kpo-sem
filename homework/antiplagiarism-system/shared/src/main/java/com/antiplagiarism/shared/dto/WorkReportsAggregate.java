// shared/src/main/java/com/antiplagiarism/shared/dto/WorkReportsAggregate.java
package com.antiplagiarism.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Агрегированная информация о работе и отчетах")
public class WorkReportsAggregate {

    @Schema(description = "Информация о работе")
    private WorkInfo work;

    @Schema(description = "Список отчетов по работе")
    private List<ReportInfo> reports;

    @Schema(description = "Общее количество отчетов")
    private int totalReports;

    @Schema(description = "Статус последнего отчета")
    private String latestReportStatus;

    @Schema(description = "Флаг плагиата (если есть хотя бы один отчет с плагиатом)")
    private boolean hasPlagiarism;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkInfo {
        private Long id;
        private String studentId;
        private String assignmentId;
        private String originalFileName;
        private String status;
        private String uploadDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportInfo {
        private Long id;
        private String status;
        private Double similarityScore;
        private boolean plagiarized;
        private String createdAt;
        private String completedAt;
    }
}