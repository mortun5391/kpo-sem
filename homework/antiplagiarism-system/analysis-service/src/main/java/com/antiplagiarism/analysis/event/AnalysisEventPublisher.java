package com.antiplagiarism.analysis.event;

import com.antiplagiarism.shared.dto.AnalysisEvent;
import com.antiplagiarism.shared.dto.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalysisEventPublisher {

    private final KafkaTemplate<String, AnalysisEvent> kafkaTemplate;

    @Value("${kafka.topic.analysis-events:analysis-events}")
    private String analysisEventsTopic;

    public void publishAnalysisEvent(EventType eventType, Long workId,
                                     String serviceName, Map<String, Object> payload) {
        try {
            AnalysisEvent event = AnalysisEvent.createWithPayload(eventType, workId, serviceName, payload);
            kafkaTemplate.send(analysisEventsTopic, event);
            log.info("Published analysis event: {} for workId: {}", eventType, workId);
        } catch (Exception e) {
            log.error("Failed to publish analysis event: {} for workId: {}", eventType, workId, e);
        }
    }

    public void publishErrorEvent(EventType eventType, Long workId, String serviceName,
                                  String errorMessage, String errorCode) {
        try {
            AnalysisEvent event = AnalysisEvent.createError(eventType, workId, serviceName, errorMessage, errorCode);
            kafkaTemplate.send(analysisEventsTopic, event);
            log.error("Published error event: {} for workId: {}, error: {}", eventType, workId, errorMessage);
        } catch (Exception e) {
            log.error("Failed to publish error event for workId: {}", workId, e);
        }
    }
}