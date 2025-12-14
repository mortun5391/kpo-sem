package com.antiplagiarism.analysis.controller;

import com.antiplagiarism.analysis.dto.AnalysisRequest;
import com.antiplagiarism.analysis.dto.AnalysisResponse;
import com.antiplagiarism.analysis.service.AnalysisService;
import com.antiplagiarism.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analyze")
@Tag(name = "Analysis Controller", description = "API для анализа работ на плагиат")
@RequiredArgsConstructor
@Slf4j
public class AnalysisController {

    private final AnalysisService analysisService;

    @Operation(summary = "Запуск анализа работы", description = "Запускает анализ работы на наличие плагиата")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Анализ принят в обработку",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnalysisResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Работа не найдена"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Анализ уже выполняется")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<AnalysisResponse>> analyzeWork(
            @Parameter(description = "Запрос на анализ", required = true)
            @Valid @RequestBody AnalysisRequest request) {

        log.info("Received analysis request for workId: {}", request.getWorkId());

        AnalysisResponse response = analysisService.startAnalysis(request.getWorkId());

        return ResponseEntity.accepted()
                .body(ApiResponse.success(response, "Analysis started successfully"));
    }

    @Operation(summary = "Получение статуса анализа", description = "Возвращает текущий статус анализа работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Статус анализа получен"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Анализ не найден")
    })
    @GetMapping("/{workId}/status")
    public ResponseEntity<ApiResponse<?>> getAnalysisStatus(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        String status = analysisService.getAnalysisStatus(workId);
        return ResponseEntity.ok(ApiResponse.success(status));
    }

    @Operation(summary = "Отмена анализа", description = "Отменяет выполняющийся анализ работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Анализ отменен"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Анализ не найден"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Анализ нельзя отменить")
    })
    @DeleteMapping("/{workId}")
    public ResponseEntity<ApiResponse<?>> cancelAnalysis(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        analysisService.cancelAnalysis(workId);
        return ResponseEntity.ok(ApiResponse.success(null, "Analysis cancelled successfully"));
    }
}