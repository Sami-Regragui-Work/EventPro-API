package com.eventpro.repository;

import com.eventpro.entity.tickets.EventTicketCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventTicketCategoryRepository extends JpaRepository<EventTicketCategory, Long> {
    @EntityGraph(attributePaths = "ticketCategory")
    List<EventTicketCategory> findByEventId(Long eventId);

    @EntityGraph(attributePaths = "ticketCategory")
    Optional<EventTicketCategory> findByIdAndEventId(Long id, Long eventId);

    boolean existsByEventIdAndTicketCategoryId(Long eventId, Long ticketCategoryId);

    @Query("""
        SELECT COALESCE(SUM(etc.totalQuantity), 0)
        FROM EventTicketCategory etc
        WHERE etc.event.id = :eventId
        """)
    long sumTotalQuantityByEventId(@Param("eventId") Long eventId);

    long countByEventId(Long eventId);
}
