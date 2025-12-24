package com.shopoholics.payments.service;

import com.shopoholics.payments.model.Account;
import com.shopoholics.payments.model.InboxEvent;
import com.shopoholics.payments.repository.InboxEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentProcessingService {
    
    @Autowired
    private InboxEventRepository inboxEventRepository;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Transactional
    public void processOrderCreatedEvent(String eventId, String payload) {
        // Проверяем, не обрабатывали ли мы это событие ранее (идемпотентность)
        InboxEvent existingEvent = inboxEventRepository.findByEventId(eventId).orElse(null);
        if (existingEvent != null && existingEvent.getStatus() == com.shopoholics.payments.model.EventStatus.PROCESSED) {
            // Событие уже обработано, ничего не делаем
            return;
        }
        
        try {
            // Создаем запись о событии в inbox, если еще не существует
            InboxEvent inboxEvent;
            if (existingEvent == null) {
                inboxEvent = new InboxEvent(eventId, "ORDER_CREATED", payload);
                inboxEvent = inboxEventRepository.save(inboxEvent);
            } else {
                inboxEvent = existingEvent;
            }
            
            // Парсим payload
            JsonNode eventData = objectMapper.readTree(payload);
            Long userId = eventData.get("userId").asLong();
            Long orderId = eventData.get("orderId").asLong();
            BigDecimal amount = new BigDecimal(eventData.get("amount").asText());
            
            // Пытаемся списать средства со счета пользователя
            try {
                // Создаем счет, если его еще нет
                Account account = paymentService.getAccountByUserId(userId).orElse(null);
                if (account == null) {
                    account = paymentService.createAccount(userId);
                }
                
                // Проверяем баланс
                if (account.getBalance().compareTo(amount) < 0) {
                    // Недостаточно средств
                    throw new RuntimeException("Insufficient funds for user " + userId);
                }
                
                // Списываем средства (с гарантией exactly-once благодаря идемпотентности)
                account.setBalance(account.getBalance().subtract(amount));
                paymentService.updateAccount(account);
                
                // Помечаем событие как обработанное
                inboxEvent.setStatus(com.shopoholics.payments.model.EventStatus.PROCESSED);
                inboxEventRepository.save(inboxEvent);
                
            } catch (Exception e) {
                // В случае ошибки помечаем событие как failed
                inboxEvent.setStatus(com.shopoholics.payments.model.EventStatus.FAILED);
                inboxEventRepository.save(inboxEvent);
                throw e;
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to process order created event", e);
        }
    }
}