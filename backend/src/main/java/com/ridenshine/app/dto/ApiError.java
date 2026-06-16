package com.ridenshine.app.dto;
import java.time.LocalDateTime;
import java.util.Map;
public record ApiError(LocalDateTime timestamp, int status, String message, String requestId, Map<String, String> validationErrors) {}
