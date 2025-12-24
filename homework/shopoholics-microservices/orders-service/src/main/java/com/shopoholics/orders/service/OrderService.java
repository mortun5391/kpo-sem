package com.shopoholics.orders.service;

import com.shopoholics.orders.model.Order;
import com.shopoholics.orders.model.OrderStatus;
import com.shopoholics.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderEventPublisher orderEventPublisher;
    
    @Transactional
    public Order createOrder(Long userId, BigDecimal amount) {
        Order order = new Order(userId, amount);
        order = orderRepository.save(order);
        
        // Публикуем событие создания заказа
        orderEventPublisher.publishOrderCreatedEvent(order);
        
        return order;
    }
    
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
    
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}