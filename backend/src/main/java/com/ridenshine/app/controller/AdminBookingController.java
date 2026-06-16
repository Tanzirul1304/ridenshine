package com.ridenshine.app.controller;
import com.ridenshine.app.dto.BookingStatusRequest;
import com.ridenshine.app.model.Booking;
import com.ridenshine.app.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/admin/bookings")
public class AdminBookingController {
    private final BookingService service;
    public AdminBookingController(BookingService service) { this.service = service; }
    @GetMapping public List<Booking> all() { return service.findAll(); }
    @PatchMapping("/{id}/status") public Booking updateStatus(@PathVariable Long id, @Valid @RequestBody BookingStatusRequest request) { return service.updateStatus(id, request.status()); }
}
