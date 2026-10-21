package com.eventpro.model;

import com.eventpro.model.enums.EventStatus;
import com.eventpro.model.tickets.TicketCategoriesPerEvent;
import com.eventpro.model.transactions.Promotion;
import com.eventpro.model.users.Organizer;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity @Table(name = "events")
@Builder @AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Instant startDate;

    private Instant endDate;

    @Column(nullable = false)
    private Instant salesOpeningDate;

    @Column(nullable = false)
    private Instant salesClosingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Organizer organizer;

    @OneToMany(mappedBy = "event")
    // don't forget to enforce at least 1
    private List<TicketCategoriesPerEvent> categories;

    @OneToMany(mappedBy = "event")
    private List<Promotion> promotions;
}