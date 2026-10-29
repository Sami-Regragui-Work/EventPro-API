package com.eventpro.repository;

import com.eventpro.domain.enums.ReservationStatus;
import com.eventpro.entity.reservations.ReservationLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ReservationLineRepository extends JpaRepository<ReservationLine, Long> {
    boolean existsByEventTicketCategoryId(Long eventTicketCategoryId);
    @Query("""
            SELECT COALESCE(SUM(rl.quantity), 0)
            FROM ReservationLine rl
            WHERE rl.eventTicketCategory.id = :eventTicketCategoryId
            AND rl.reservation.status in :statuses
            """)
    long sumQuantityByRowAndStatuses(
            @Param("eventTicketCategoryId") Long eventTicketCategoryId,
            @Param("statuses") Collection<ReservationStatus> statuses
            );
}
