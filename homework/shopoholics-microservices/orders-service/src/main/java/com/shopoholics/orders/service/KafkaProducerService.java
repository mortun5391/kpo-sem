package com.shopoholics.orders.service;

import com.shopoholics.orders.model.OutboxEvent;
import com.shopoholics.orders.repository.OutboxEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KafkaProducerService {
    
    @Autowired
    private OutboxEventRepository outboxEventRepository;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private OrderEventPublisher orderEventPublisher;
    
    @Scheduled(fixedDelay = 5000) // Проверяем каждые 5 секунд
    @Transactional
    public void processOutboxEvents() {
        try {
            // Получаем все непrocessed события
            List<OutboxEvent> pendingEvents = outboxEventRepository.findByStatusOrderByCreatedAtAsc(
                    com.shopoholics.orders.model.EventStatus.PENDING);
            
            for (OutboxEvent event : pendingEvents) {
                try {
                    // Отправляем событие в Kafka
                    kafkaTemplate.send("order-events", event.getId().toString(), event.getPayload());
                    
                    // Помечаем событие как обработанное
                    orderEventPublisher.markEventAsProcessed(event.getId());
                } catch (Exception e) {
                    // В случае ошибки помечаем событие как failed
                    event.setStatus(com.shopoholics.orders.model.EventStatus.FAILED);
                    outboxEventRepository.save(event);
                    // Продолжаем обработку следующих событий
                }
            }
        } catch (Exception e) {
            // Логируем ошибку, но не прерываем выполнение
            e.printStackTrace();
        }
    }
}