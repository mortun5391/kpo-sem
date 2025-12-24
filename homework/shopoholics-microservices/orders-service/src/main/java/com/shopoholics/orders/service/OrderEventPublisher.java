package com.shopoholics.orders.service;

import com.shopoholics.orders.model.OutboxEvent;
import com.shopoholics.orders.model.Order;
import com.shopoholics.orders.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderEventPublisher {
    
    @Autowired
    private OutboxEventRepository outboxEventRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Transactional
    public void publishOrderCreatedEvent(Order order) {
        try {
            // Создаем payload для события
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("orderId", order.getId());
            eventData.put("userId", order.getUserId());
            eventData.put("amount", order.getAmount());
            eventData.put("createdAt", order.getCreatedAt());
            
            String payload = objectMapper.writeValueAsString(eventData);
            
            // Создаем событие в outbox
            OutboxEvent event = new OutboxEvent("ORDER_CREATED", payload);
            outboxEventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish order created event", e);
        }
    }
    
    @Transactional
    public void markEventAsProcessed(Long eventId) {
        OutboxEvent event = outboxEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        event.setStatus(com.shopoholics.orders.model.EventStatus.PROCESSED);
        event.setProcessedAt(LocalDateTime.now());
        outboxEventRepository.save(event);
    }
}