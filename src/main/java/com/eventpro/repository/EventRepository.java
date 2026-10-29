package com.eventpro.repository;

import com.eventpro.entity.Event;
import com.eventpro.domain.enums.EventStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.Instant;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @EntityGraph(attributePaths = "organizer")
    Page<Event> findByStatusAndSalesClosingDateAfter(EventStatus status, Instant now, Pageable pageable);

    @EntityGraph(attributePaths = "organizer")
    Optional<Event> findByIdAndStatus(Long id, EventStatus status);

    @EntityGraph(attributePaths = "organizer")
    Page<Event> findByOrganizerId(Long organizerId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Event> findWithLockById(Long id);
}
