package com.eventpro.advice;

import com.eventpro.dto.response.ErrorResponse;
import com.eventpro.exception.ResourceNotFoundException;
import com.eventpro.exception.SystemException;
import com.eventpro.exception.ValidationException;
import com.eventpro.exception.reservations.InsufficientStockException;
import com.eventpro.exception.reservations.ReservationExpiredException;
import com.eventpro.exception.tickets.TicketAlreadyUsedException;
import com.eventpro.exception.transactions.IdempotencyConflictException;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400 for validation violation
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        HttpStatus statusBadReq = HttpStatus.BAD_REQUEST;
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> fieldErrors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));

        String message = ex instanceof ValidationException ve ? ve.getMessage() : "Validation failed";

        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .status(statusBadReq.value())
                        .error(statusBadReq.getReasonPhrase())
                        .message(message)
                        .path(GlobalExceptionHandler.getWebRequestPath(request))
                        .fieldErrors(fieldErrors)
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 404
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest req) {
        return this.buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage(), req);
    }

    @ExceptionHandler(InsufficientStockException.class) // 409
    public ResponseEntity<ErrorResponse> handleInsufficientStock(InsufficientStockException e, HttpServletRequest req) {
        return this.buildResponseEntity(HttpStatus.CONFLICT, e.getMessage(), req);
    }

    @ExceptionHandler(ReservationExpiredException.class) // 409
    public ResponseEntity<ErrorResponse> handleReservationExpired(ReservationExpiredException e, HttpServletRequest req) {
        return this.buildResponseEntity(HttpStatus.CONFLICT, e.getMessage(), req);
    }

    @ExceptionHandler(TicketAlreadyUsedException.class) // 409
    public ResponseEntity<ErrorResponse> handleTicketAlreadyUsed(TicketAlreadyUsedException e, HttpServletRequest req) {
        return this.buildResponseEntity(HttpStatus.CONFLICT, e.getMessage(), req);
    }

    @ExceptionHandler(IdempotencyConflictException.class) // 409
    public ResponseEntity<ErrorResponse> handleIdempotencyConflict(IdempotencyConflictException e, HttpServletRequest req) {
        return this.buildResponseEntity(HttpStatus.CONFLICT, e.getMessage(), req);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, String message, HttpServletRequest req) {
        return ResponseEntity.status(status).body(this.buildErrorResponse(status, message, req));
    }

    @ExceptionHandler(SystemException.class) // 500
    public ResponseEntity<ErrorResponse> handleSystem(SystemException e, HttpServletRequest req) {
        log.error("System failure on {}", req.getRequestURI(), e);
        return this.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req);
    }

    @ExceptionHandler(Exception.class) // 500, anything not matched above or by Spring
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception e, HttpServletRequest req) {
        log.error("Unhandled exception on {}", req.getRequestURI(), e);
        return this.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req);
    }

    private ErrorResponse buildErrorResponse(HttpStatus status, String message, HttpServletRequest req) {
        return ErrorResponse.builder().status(status.value()).error(status.getReasonPhrase()).message(message).path(req.getRequestURI()).build();
    }

    private static String getWebRequestPath(WebRequest request) {
        return request instanceof ServletWebRequest servletWebRequest ? servletWebRequest.getRequest().getRequestURI() : "";
    }
}
