package com.eventpro.entity.tickets;

import com.eventpro.entity.Event;
import com.eventpro.entity.reservations.ReservationLine;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity @Table(
        name = "ticket_categories_per_events",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_event_category",
                columnNames = {"event_id", "category_id"}
        )
)
@Builder @AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class TicketCategoriesPerEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int totalQuantity;

    @Column(nullable = false)
    private int maxQuotaPerClient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Event event;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TicketCategory category;

    @OneToMany(mappedBy = "category")
    private List<ReservationLine> reservationLines;
}