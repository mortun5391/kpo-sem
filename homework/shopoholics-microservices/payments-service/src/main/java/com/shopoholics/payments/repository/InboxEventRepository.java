package com.shopoholics.payments.repository;

import com.shopoholics.payments.model.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InboxEventRepository extends JpaRepository<InboxEvent, Long> {
    
    Optional<InboxEvent> findByEventId(String eventId);
}