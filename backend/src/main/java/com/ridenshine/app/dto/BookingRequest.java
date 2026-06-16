package com.ridenshine.app.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record BookingRequest(
        @NotBlank String customerName,
        @NotBlank @Pattern(regexp = "^[+0-9][0-9 -]{7,18}$", message = "Enter a valid phone number") String phone,
        @NotBlank String bikeModel,
        @NotNull Long serviceId,
        @NotNull @FutureOrPresent LocalDate preferredDate,
        @Size(max = 1200) String notes
) {}
