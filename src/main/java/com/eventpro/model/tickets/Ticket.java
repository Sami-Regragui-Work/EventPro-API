package com.eventpro.model.tickets;

import com.eventpro.model.enums.TicketStatus;
import com.eventpro.model.reservations.ReservationLine;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity @Table(name = "tickets")
@Builder @AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant issueDate;

    private Instant scanDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ReservationLine reservationLine;
}