package com.ridenshine.app.exception;

import com.ridenshine.app.dto.ApiError;
import com.ridenshine.app.model.ApplicationError;
import com.ridenshine.app.repository.ApplicationErrorRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ApplicationErrorRepository errorRepository;
    public GlobalExceptionHandler(ApplicationErrorRepository errorRepository) { this.errorRepository = errorRepository; }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiError> notFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(LocalDateTime.now(), 404, ex.getMessage(), null, Map.of()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(e -> e.getField(), e -> Objects.requireNonNullElse(e.getDefaultMessage(), "Invalid value"), (a,b) -> a));
        return ResponseEntity.badRequest().body(new ApiError(LocalDateTime.now(), 400, "Validation failed", null, fields));
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> unexpected(Exception ex, HttpServletRequest request) {
        String requestId = UUID.randomUUID().toString();
        log.error("Unhandled error requestId={} path={}", requestId, request.getRequestURI(), ex);
        try {
            ApplicationError tracked = new ApplicationError(); tracked.setRequestId(requestId); tracked.setOccurredAt(LocalDateTime.now());
            tracked.setPath(request.getRequestURI()); tracked.setMessage(Objects.requireNonNullElse(ex.getMessage(), "Unknown error")); tracked.setExceptionType(ex.getClass().getSimpleName());
            errorRepository.save(tracked);
        } catch (Exception trackingFailure) { log.error("Could not persist error event", trackingFailure); }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(LocalDateTime.now(), 500, "Unexpected server error", requestId, Map.of()));
    }
}
