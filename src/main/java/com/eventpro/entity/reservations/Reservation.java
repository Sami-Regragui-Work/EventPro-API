package com.eventpro.entity.reservations;

import com.eventpro.domain.enums.ReservationStatus;
import com.eventpro.entity.transactions.Payment;
import com.eventpro.entity.transactions.Promotion;
import com.eventpro.entity.users.Client;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity @Table(name = "reservations")
@Builder @AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant creationDate;

    @Column(nullable = false)
    private Instant expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationLine> reservationLines;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;
}