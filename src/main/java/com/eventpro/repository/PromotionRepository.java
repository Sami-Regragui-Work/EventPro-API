package com.eventpro.repository;

import com.eventpro.entity.transactions.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByEventId(Long eventId);
    Optional<Promotion> findByIdAndEventId(Long id, Long eventId);
    boolean existsByEventIdAndCode(Long eventId, String code);
}