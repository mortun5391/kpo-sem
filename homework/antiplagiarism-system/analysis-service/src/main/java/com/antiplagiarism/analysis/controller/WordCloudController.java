package com.antiplagiarism.analysis.controller;

import com.antiplagiarism.analysis.dto.WordCloudRequest;
import com.antiplagiarism.analysis.service.WordCloudService;
import com.antiplagiarism.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/wordcloud")
@Tag(name = "Word Cloud Controller", description = "API для генерации облаков слов")
@RequiredArgsConstructor
@Slf4j
public class WordCloudController {

    private final WordCloudService wordCloudService;

    @Operation(summary = "Генерация облака слов", description = "Генерирует облако слов для работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Облако слов сгенерировано"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Работа не найдена")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<?>> generateWordCloud(
            @Parameter(description = "Запрос на генерацию облака слов", required = true)
            @Valid @RequestBody WordCloudRequest request) {

        log.info("Generating word cloud for workId: {}", request.getWorkId());

        String wordCloudUrl = wordCloudService.generateWordCloud(request);

        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "wordCloudUrl", wordCloudUrl,
                "workId", request.getWorkId()
        ), "Word cloud generated successfully"));
    }

    @Operation(summary = "Получение облака слов", description = "Возвращает URL облака слов для работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "URL облака слов получен"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Облако слов не найдено")
    })
    @GetMapping("/{workId}")
    public ResponseEntity<ApiResponse<?>> getWordCloud(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        String wordCloudUrl = wordCloudService.getWordCloud(workId);

        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "wordCloudUrl", wordCloudUrl,
                "workId", workId
        )));
    }

    @Operation(summary = "Обновление облака слов", description = "Обновляет облако слов для работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Облако слов обновлено"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Работа не найдена")
    })
    @PutMapping("/{workId}")
    public ResponseEntity<ApiResponse<?>> updateWordCloud(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        WordCloudRequest request = WordCloudRequest.builder()
                .workId(workId)
                .build();

        String wordCloudUrl = wordCloudService.generateWordCloud(request);

        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "wordCloudUrl", wordCloudUrl,
                "workId", workId
        ), "Word cloud updated successfully"));
    }

    @Operation(summary = "Удаление облака слов", description = "Удаляет облако слов для работы")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Облако слов удалено"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Облако слов не найдено")
    })
    @DeleteMapping("/{workId}")
    public ResponseEntity<ApiResponse<?>> deleteWordCloud(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        wordCloudService.deleteWordCloud(workId);
        return ResponseEntity.ok(ApiResponse.success(null, "Word cloud deleted successfully"));
    }
}