package com.antiplagiarism.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Событие в системе анализа плагиата")
public class AnalysisEvent {

    @NotNull
    @Schema(description = "Уникальный идентификатор события", example = "123e4567-e89b-12d3-a456-426614174000")
    private String eventId;

    @NotNull
    @Schema(description = "Тип события")
    private EventType eventType;

    @NotNull
    @Schema(description = "Время создания события")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Идентификатор работы")
    private Long workId;

    @Schema(description = "Идентификатор студента")
    private String studentId;

    @Schema(description = "Идентификатор задания")
    private String assignmentId;

    @Schema(description = "Идентификатор отчета")
    private Long reportId;

    @Schema(description = "Имя сервиса, сгенерировавшего событие")
    private String serviceName;

    @Schema(description = "Дополнительные данные события")
    private Map<String, Object> payload;

    @Schema(description = "Сообщение об ошибке (если есть)")
    private String errorMessage;

    @Schema(description = "Код ошибки (если есть)")
    private String errorCode;

    public static AnalysisEvent create(EventType eventType, Long workId, String serviceName) {
        return AnalysisEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .timestamp(LocalDateTime.now())
                .workId(workId)
                .serviceName(serviceName)
                .build();
    }

    public static AnalysisEvent createWithPayload(EventType eventType, Long workId,
                                                  String serviceName, Map<String, Object> payload) {
        return AnalysisEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .timestamp(LocalDateTime.now())
                .workId(workId)
                .serviceName(serviceName)
                .payload(payload)
                .build();
    }

    public static AnalysisEvent createError(EventType eventType, Long workId,
                                            String serviceName, String errorMessage, String errorCode) {
        return AnalysisEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .timestamp(LocalDateTime.now())
                .workId(workId)
                .serviceName(serviceName)
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .build();
    }
}