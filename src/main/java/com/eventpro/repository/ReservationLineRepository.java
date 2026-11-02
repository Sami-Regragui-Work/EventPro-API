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
    long sumQuantityByEventTicketCategoryIdAndReservationStatuses(
            @Param("eventTicketCategoryId") Long eventTicketCategoryId,
            @Param("statuses") Collection<ReservationStatus> statuses
            );

    @Query("""
            SELECT COALESCE(SUM(rl.quantity), 0)
            FROM ReservationLine rl
            WHERE rl.eventTicketCategory.id = :eventTicketCategoryId
            AND rl.reservation.client.id = :clientId
            AND rl.reservation.status IN :statuses
            """)
    long sumQuantityByEventTicketCategoryIdAndClientIdAndReservationStatuses(
            @Param("eventTicketCategoryId") Long eventTicketCategoryId,
            @Param("clientId") Long clientId,
            @Param("statuses") Collection<ReservationStatus> statuses
    );
}
