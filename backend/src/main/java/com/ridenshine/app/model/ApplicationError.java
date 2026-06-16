package com.ridenshine.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_errors")
public class ApplicationError {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String requestId;
    @Column(nullable = false) private LocalDateTime occurredAt;
    @Column(nullable = false) private String path;
    @Column(nullable = false, length = 1500) private String message;
    private String exceptionType;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getExceptionType() { return exceptionType; }
    public void setExceptionType(String exceptionType) { this.exceptionType = exceptionType; }
}
