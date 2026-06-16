package com.ridenshine.app.service;

import com.ridenshine.app.dto.BookingRequest;
import com.ridenshine.app.exception.ResourceNotFoundException;
import com.ridenshine.app.model.*;
import com.ridenshine.app.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository repository;
    private final WorkshopServiceManager serviceManager;
    public BookingService(BookingRepository repository, WorkshopServiceManager serviceManager) { this.repository = repository; this.serviceManager = serviceManager; }
    public Booking create(BookingRequest request) {
        Booking booking = new Booking();
        booking.setCustomerName(request.customerName()); booking.setPhone(request.phone()); booking.setBikeModel(request.bikeModel());
        booking.setService(serviceManager.findById(request.serviceId())); booking.setPreferredDate(request.preferredDate()); booking.setNotes(request.notes());
        Booking saved = repository.save(booking);
        log.info("Booking created: reference={}, service={}, date={}", saved.getReferenceCode(), saved.getService().getName(), saved.getPreferredDate());
        return saved;
    }
    public Booking findByReference(String reference) { return repository.findByReferenceCodeIgnoreCase(reference).orElseThrow(() -> new ResourceNotFoundException("Booking reference not found")); }
    public List<Booking> findAll() { return repository.findAllByOrderByCreatedAtDesc(); }
    public Booking updateStatus(Long id, BookingStatus status) {
        Booking booking = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(status); Booking saved = repository.save(booking);
        log.info("Booking status updated: reference={}, status={}", saved.getReferenceCode(), saved.getStatus());
        return saved;
    }
}
