package com.antiplagiarism.filestoring.controller;

import com.antiplagiarism.filestoring.dto.UploadRequest;
import com.antiplagiarism.filestoring.dto.UploadResponse;
import com.antiplagiarism.filestoring.service.FileStorageService;
import com.antiplagiarism.shared.constants.ApiConstants;
import com.antiplagiarism.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "File Controller", description = "API для работы с файлами")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @Operation(summary = "Загрузка файла", description = "Загружает файл работы студента")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Файл успешно загружен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "413", description = "Размер файла превышает допустимый"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "415", description = "Неподдерживаемый тип файла")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UploadResponse>> uploadFile(
            @Parameter(description = "Файл для загрузки", required = true)
            @RequestParam("file") MultipartFile file,

            @Parameter(description = "ID студента", required = true)
            @RequestParam("studentId") String studentId,

            @Parameter(description = "ID задания", required = true)
            @RequestParam("assignmentId") String assignmentId) {

        UploadRequest request = UploadRequest.builder()
                .file(file)
                .studentId(studentId)
                .assignmentId(assignmentId)
                .build();

        UploadResponse response = fileStorageService.storeFile(request);
        return ResponseEntity.ok(ApiResponse.success(response, "File uploaded successfully"));
    }

    @Operation(summary = "Скачивание файла", description = "Скачивает файл работы по ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Файл найден"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Файл не найден")
    })
    @GetMapping("/{workId}/file")
    public ResponseEntity<Resource> downloadFile(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long workId) {

        Resource resource = fileStorageService.loadFileAsResource(workId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}