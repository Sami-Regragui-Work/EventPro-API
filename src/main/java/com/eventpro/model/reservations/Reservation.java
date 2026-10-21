package com.eventpro.model.reservations;

import com.eventpro.model.enums.ReservationStatus;
import com.eventpro.model.transactions.Promotion;
import com.eventpro.model.users.Client;
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
    // don't forget to add @PrePersist
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
}