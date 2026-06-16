package com.ridenshine.app.dto;
import com.ridenshine.app.model.BookingStatus;
import jakarta.validation.constraints.NotNull;
public record BookingStatusRequest(@NotNull BookingStatus status) {}
