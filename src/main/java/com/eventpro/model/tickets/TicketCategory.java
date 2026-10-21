package com.eventpro.model.tickets;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "ticket_categories")
@Builder @AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class TicketCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
