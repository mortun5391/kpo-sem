package com.antiplagiarism.analysis.controller;

import com.antiplagiarism.analysis.dto.ReportDTO;
import com.antiplagiarism.analysis.service.ReportService;
import com.antiplagiarism.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Report Controller", description = "API для работы с отчетами")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Получение отчета", description = "Возвращает отчет по его ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Отчет найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Отчет не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReportDTO>> getReportById(
            @Parameter(description = "ID отчета", required = true)
            @PathVariable Long id) {

        ReportDTO report = reportService.getReportById(id);
        return ResponseEntity.ok(ApiResponse.success(report));
    }

    @Operation(summary = "Получение всех отчетов по работе", description = "Возвращает все отчеты для конкретной работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Отчеты найдены",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportDTO.class))))
    })
    @GetMapping("/work/{workId}")
    public ResponseEntity<ApiResponse<List<ReportDTO>>> getReportsByWorkId(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        List<ReportDTO> reports = reportService.getReportsByWorkId(workId);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    @Operation(summary = "Получение последнего отчета по работе",
            description = "Возвращает последний отчет для конкретной работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Отчет найден"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Отчеты не найдены")
    })
    @GetMapping("/work/{workId}/latest")
    public ResponseEntity<ApiResponse<ReportDTO>> getLatestReportByWorkId(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        ReportDTO report = reportService.getLatestReportByWorkId(workId);
        return ResponseEntity.ok(ApiResponse.success(report));
    }

    @Operation(summary = "Удаление отчета", description = "Удаляет отчет по его ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Отчет удален"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Отчет не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteReport(
            @Parameter(description = "ID отчета", required = true)
            @PathVariable Long id) {

        reportService.deleteReport(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Report deleted successfully"));
    }
}