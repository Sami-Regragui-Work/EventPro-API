package com.eventpro.controller.tickets;

import com.eventpro.dto.request.tickets.AttachTicketCategoryRequest;
import com.eventpro.dto.request.tickets.UpdateTicketCategoryRequest;
import com.eventpro.dto.response.tickets.EventTicketCategoryResponse;
import com.eventpro.exception.badrequest.ValidationException;
import com.eventpro.mapper.EventTicketCategoryMapper;
import com.eventpro.service.EventTicketCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizers/{organizerId}/events/{eventId}/ticket-categories")
@RequiredArgsConstructor
public class EventTicketCategoryController {
    private final EventTicketCategoryService service;
    private final EventTicketCategoryMapper mapper;

    @GetMapping
    public List<EventTicketCategoryResponse> list(
            @PathVariable Long organizerId,
            @PathVariable Long eventId
    ) {
        return this.service.list(organizerId, eventId).stream().map(this.mapper::toResponse).toList();
    }

    @PostMapping
    public ResponseEntity<EventTicketCategoryResponse> attach(
            @PathVariable Long organizerId,
            @PathVariable Long eventId,
            @Valid @RequestBody
            AttachTicketCategoryRequest dto
    ) throws ValidationException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.mapper.toResponse(this.service.attach(organizerId, eventId, dto)));
    }

    @PatchMapping("/{id}")
    public EventTicketCategoryResponse update(
            @PathVariable Long organizerId,
            @PathVariable Long eventId,
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateTicketCategoryRequest dto
    ) throws ValidationException
    {
        return this.mapper.toResponse(this.service.update(organizerId, eventId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(
            @PathVariable Long organizerId,
            @PathVariable Long eventId,
            @PathVariable Long id
    )
    {
        this.service.remove(organizerId, eventId, id);
        return ResponseEntity.noContent().build();
    }
}
