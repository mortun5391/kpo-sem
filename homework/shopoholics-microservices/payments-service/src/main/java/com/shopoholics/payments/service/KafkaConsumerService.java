package com.shopoholics.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    
    @Autowired
    private PaymentProcessingService paymentProcessingService;
    
    @KafkaListener(topics = "order-events", groupId = "payments-group")
    public void handleOrderEvents(String message, org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record) {
        try {
            String eventId = record.key();
            String payload = record.value();
            
            // Обрабатываем событие создания заказа
            paymentProcessingService.processOrderCreatedEvent(eventId, payload);
        } catch (Exception e) {
            // В production среде здесь должна быть proper error handling и возможно dead letter queue
            e.printStackTrace();
        }
    }
}