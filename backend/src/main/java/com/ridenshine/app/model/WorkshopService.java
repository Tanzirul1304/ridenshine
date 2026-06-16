package com.ridenshine.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "workshop_services")
public class WorkshopService {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false) private String name;
    @Column(length = 1200) private String description;
    @NotNull @DecimalMin("0.0") @Column(nullable = false) private BigDecimal price;
    @NotNull @Min(5) @Column(nullable = false) private Integer durationMinutes;
    @Column(nullable = false) private boolean active = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
