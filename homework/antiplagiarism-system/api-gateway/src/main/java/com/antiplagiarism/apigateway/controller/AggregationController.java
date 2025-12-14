package com.antiplagiarism.apigateway.controller;

import com.antiplagiarism.apigateway.service.AnalysisServiceClient;
import com.antiplagiarism.apigateway.service.FileServiceClient;
import com.antiplagiarism.shared.dto.ApiResponse;
import com.antiplagiarism.shared.dto.WorkReportsAggregate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/aggregate")
@Tag(name = "Aggregation Controller", description = "API для агрегации данных из разных сервисов")
@RequiredArgsConstructor
@Slf4j
public class AggregationController {

    private final FileServiceClient fileServiceClient;
    private final AnalysisServiceClient analysisServiceClient;

    @Operation(summary = "Получить работу и все отчеты по ней",
            description = "Агрегирует данные из file-storing-service и analysis-service")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Агрегированные данные получены"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Работа не найдена"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "503",
                    description = "Один из сервисов недоступен"
            )
    })
    @GetMapping("/works/{workId}/reports")
    public Mono<ResponseEntity<ApiResponse<WorkReportsAggregate>>> getWorkWithReports(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        log.info("Aggregating data for workId: {}", workId);

        return fileServiceClient.getWorkInfo(workId)
                .flatMap(workResponse -> {
                    if (workResponse.getData() == null) {
                        return Mono.just(ResponseEntity.status(404)
                                .body(ApiResponse.<WorkReportsAggregate>error(
                                        "Work not found",
                                        "WORK_NOT_FOUND")));
                    }

                    @SuppressWarnings("unchecked")
                    Map<String, Object> workData = (Map<String, Object>) workResponse.getData();

                    return analysisServiceClient.getWorkReports(workId)
                            .map(reportsResponse -> {
                                @SuppressWarnings("unchecked")
                                List<Map<String, Object>> reportsData =
                                        reportsResponse.getData() != null ?
                                                (List<Map<String, Object>>) reportsResponse.getData() :
                                                List.of();

                                WorkReportsAggregate aggregate = buildAggregate(workData, reportsData);
                                return ResponseEntity.ok(ApiResponse.success(aggregate));
                            })
                            .onErrorResume(e -> {
                                log.error("Error getting reports for workId: {}", workId, e);
                                WorkReportsAggregate aggregate = buildAggregate(workData, List.of());
                                return Mono.just(ResponseEntity.ok(
                                        ApiResponse.success(aggregate,
                                                "Work found, but reports service unavailable")));
                            });
                })
                .onErrorResume(e -> {
                    log.error("Error getting work info for workId: {}", workId, e);
                    return Mono.just(ResponseEntity.status(503)
                            .body(ApiResponse.<WorkReportsAggregate>error(
                                    "File service unavailable",
                                    "SERVICE_UNAVAILABLE")));
                });
    }

    private WorkReportsAggregate buildAggregate(Map<String, Object> workData,
                                                List<Map<String, Object>> reportsData) {
        // Преобразуем данные в агрегированный ответ
        WorkReportsAggregate.WorkInfo workInfo = WorkReportsAggregate.WorkInfo.builder()
                .id(workData.get("id") != null ?
                        Long.valueOf(workData.get("id").toString()) : null)
                .studentId((String) workData.get("studentId"))
                .assignmentId((String) workData.get("assignmentId"))
                .originalFileName((String) workData.get("originalFileName"))
                .status((String) workData.get("status"))
                .uploadDate(workData.get("uploadDate") != null ?
                        workData.get("uploadDate").toString() : null)
                .build();

        List<WorkReportsAggregate.ReportInfo> reportInfos = reportsData.stream()
                .map(report -> WorkReportsAggregate.ReportInfo.builder()
                        .id(report.get("id") != null ?
                                Long.valueOf(report.get("id").toString()) : null)
                        .status((String) report.get("status"))
                        .similarityScore(report.get("similarityScore") != null ?
                                Double.valueOf(report.get("similarityScore").toString()) : null)
                        .plagiarized(Boolean.TRUE.equals(report.get("plagiarized")))
                        .createdAt(report.get("createdAt") != null ?
                                report.get("createdAt").toString() : null)
                        .completedAt(report.get("completedAt") != null ?
                                report.get("completedAt").toString() : null)
                        .build())
                .toList();

        boolean hasPlagiarism = reportInfos.stream()
                .anyMatch(report -> report != null && report.isPlagiarized());

        String latestStatus = reportInfos.isEmpty() ? "NO_REPORTS" :
                reportInfos.get(0).getStatus();

        return WorkReportsAggregate.builder()
                .work(workInfo)
                .reports(reportInfos)
                .totalReports(reportInfos.size())
                .latestReportStatus(latestStatus)
                .hasPlagiarism(hasPlagiarism)
                .build();
    }

    @GetMapping("/fallback/aggregate")
    public ResponseEntity<ApiResponse<?>> aggregateFallback() {
        return ResponseEntity.status(503)
                .body(ApiResponse.error("Aggregation service temporarily unavailable",
                        "AGGREGATION_SERVICE_UNAVAILABLE"));
    }
}