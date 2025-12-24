package com.shopoholics.orders.repository;

import com.shopoholics.orders.model.OutboxEvent;
import com.shopoholics.orders.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
    
    List<OutboxEvent> findByStatusOrderByCreatedAtAsc(EventStatus status);
}