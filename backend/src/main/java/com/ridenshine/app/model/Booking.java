package com.ridenshine.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) private String referenceCode;
    @NotBlank @Column(nullable = false) private String customerName;
    @NotBlank @Column(nullable = false) private String phone;
    @NotBlank @Column(nullable = false) private String bikeModel;
    @ManyToOne(optional = false) private WorkshopService service;
    @NotNull @Column(nullable = false) private LocalDate preferredDate;
    @Column(length = 1200) private String notes;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private BookingStatus status = BookingStatus.REQUESTED;
    @Column(nullable = false) private LocalDateTime createdAt;

    @PrePersist void prePersist() {
        if (referenceCode == null) referenceCode = "RNS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReferenceCode() { return referenceCode; }
    public void setReferenceCode(String referenceCode) { this.referenceCode = referenceCode; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getBikeModel() { return bikeModel; }
    public void setBikeModel(String bikeModel) { this.bikeModel = bikeModel; }
    public WorkshopService getService() { return service; }
    public void setService(WorkshopService service) { this.service = service; }
    public LocalDate getPreferredDate() { return preferredDate; }
    public void setPreferredDate(LocalDate preferredDate) { this.preferredDate = preferredDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
