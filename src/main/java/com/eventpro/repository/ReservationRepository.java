package com.eventpro.repository;

import com.eventpro.domain.enums.ReservationStatus;
import com.eventpro.entity.reservations.Reservation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Reservation> findWithLockById(Long id);

    @EntityGraph(attributePaths = {"promotion", "reservationLines"})
    Optional<Reservation> findByReference(String reference);

    boolean existsByReference(String reference);

    @EntityGraph(attributePaths = "promotion")
    List<Reservation> findByStatusAndExpirationDateLessThanEqual(ReservationStatus status, Instant now);
}
