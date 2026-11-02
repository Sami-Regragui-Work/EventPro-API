package com.eventpro.controller;

import com.eventpro.dto.request.reservations.CreateReservationRequest;
import com.eventpro.dto.response.reservations.ReservationResponse;
import com.eventpro.exception.badrequest.ValidationException;
import com.eventpro.mapper.ReservationMapper;
import com.eventpro.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients/{clientId}/events/{eventId}/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService service;
    private final ReservationMapper mapper;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
        @PathVariable Long clientId,
        @PathVariable Long eventId,
        @Valid @RequestBody
        CreateReservationRequest dto
    ) throws ValidationException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.mapper.toResponse(this.service.create(clientId, eventId, dto)));
    }
}
