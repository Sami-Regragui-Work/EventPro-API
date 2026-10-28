package com.eventpro.model.transactions;

import com.eventpro.model.Event;
import com.eventpro.model.enums.DiscountType;
import com.eventpro.model.reservations.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "promotions")
@Builder @AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class Promotion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType type;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private int usageLimit;

    private int usageCount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Event event;

    @OneToMany(mappedBy = "promotion")
    private List<Reservation> reservations;
}