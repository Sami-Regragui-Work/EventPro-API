package com.eventpro.controller.events;

import com.eventpro.dto.response.PageResponse;
import com.eventpro.dto.response.events.EventSummaryResponse;
import com.eventpro.mapper.EventMapper;
import com.eventpro.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService service;
    private final EventMapper mapper;

    @GetMapping
    public PageResponse<EventSummaryResponse> list(
            @PageableDefault(size = 20, sort = "startDate")
            Pageable pageable
    )
    {
        return PageResponse.of(this.service.findPublic(pageable).map(this.mapper::toSummaryResponse));
    }

    @GetMapping("/{id}")
    public EventSummaryResponse get(@PathVariable Long id) {
        return this.mapper.toSummaryResponse(this.service.findPublicById(id));
    }
}
