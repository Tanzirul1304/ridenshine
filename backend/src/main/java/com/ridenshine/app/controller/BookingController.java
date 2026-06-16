package com.ridenshine.app.controller;
import com.ridenshine.app.dto.BookingRequest;
import com.ridenshine.app.model.Booking;
import com.ridenshine.app.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Booking create(@Valid @RequestBody BookingRequest request) { return service.create(request); }
    @GetMapping("/reference/{reference}") public Booking status(@PathVariable String reference) { return service.findByReference(reference); }
}
