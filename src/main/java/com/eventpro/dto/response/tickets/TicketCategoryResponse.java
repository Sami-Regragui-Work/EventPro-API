package com.eventpro.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class TicketCategoryResponse {
    private final Long id;
    private final String name;
}
