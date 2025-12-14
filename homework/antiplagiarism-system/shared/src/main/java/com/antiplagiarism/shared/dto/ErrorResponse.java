package com.antiplagiarism.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Стандартный ответ с ошибкой")
public class ErrorResponse {

    @Schema(description = "HTTP статус код", example = "400")
    private int status;

    @Schema(description = "Сообщение об ошибке", example = "Validation failed")
    private String message;

    @Schema(description = "Код ошибки", example = "VALIDATION_ERROR")
    private String errorCode;

    @Schema(description = "Время возникновения ошибки")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Путь запроса", example = "/api/v1/files/upload")
    private String path;

    @Schema(description = "Детали ошибки")
    private Map<String, Object> details;

    public static ErrorResponse create(int status, String message, String path) {
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    public static ErrorResponse create(int status, String message, String errorCode, String path) {
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    public static ErrorResponse create(int status, String message, String errorCode,
                                       String path, Map<String, Object> details) {
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .path(path)
                .details(details)
                .build();
    }
}