package com.antiplagiarism.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EventType {
    FILE_UPLOADED("file.uploaded"),
    FILE_PROCESSING_STARTED("file.processing.started"),
    FILE_PROCESSING_COMPLETED("file.processing.completed"),
    ANALYSIS_STARTED("analysis.started"),
    ANALYSIS_COMPLETED("analysis.completed"),
    PLAGIARISM_DETECTED("plagiarism.detected"),
    REPORT_GENERATED("report.generated"),
    WORDCLOUD_GENERATED("wordcloud.generated"),
    ERROR_OCCURRED("error.occurred"),
    SERVICE_HEALTH_CHANGED("service.health.changed");

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static EventType fromCode(String code) {
        for (EventType type : EventType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + code);
    }
}