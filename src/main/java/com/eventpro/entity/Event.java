package com.eventpro.entity;

import com.eventpro.exception.events.IllegalEventStateException;
import com.eventpro.domain.enums.EventStatus;
import com.eventpro.entity.tickets.TicketCategoriesPerEvent;
import com.eventpro.entity.transactions.Promotion;
import com.eventpro.entity.users.Organizer;
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
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private EventStatus status = EventStatus.DRAFT;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Organizer organizer;

    @OneToMany(mappedBy = "event")
    // don't forget to enforce at least 1
    private List<TicketCategoriesPerEvent> categories;

    @OneToMany(mappedBy = "event")
    private List<Promotion> promotions;

    public void assertCanTransitionTo(EventStatus desiredStatus) {
        if (!this.status.canTransitionTo(desiredStatus)) {
            throw new IllegalEventStateException(this.status.toString(), desiredStatus.toString());
        }
    }

    public void transitionTo(EventStatus desiredStatus) {
        this.assertCanTransitionTo(desiredStatus);
        this.status = desiredStatus;
    }

    public boolean isDraft() {
        return this.status.equals(EventStatus.DRAFT);
    }

    public void requireModifiable() {
        if (!this.status.canBeEdited())
            throw new IllegalEventStateException("Event is " + this.status + " and cannot be edited");
    }
}