package com.ridenshine.app.service;

import com.ridenshine.app.dto.DashboardStats;
import com.ridenshine.app.model.BookingStatus;
import com.ridenshine.app.repository.*;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {
    private final ProductRepository products; private final WorkshopServiceRepository services; private final BookingRepository bookings;
    private final ReviewRepository reviews; private final ApplicationErrorRepository errors;
    public DashboardService(ProductRepository products, WorkshopServiceRepository services, BookingRepository bookings, ReviewRepository reviews, ApplicationErrorRepository errors) {
        this.products = products; this.services = services; this.bookings = bookings; this.reviews = reviews; this.errors = errors;
    }
    public DashboardStats stats() {
        Map<String, Long> byStatus = new LinkedHashMap<>();
        for (BookingStatus status : BookingStatus.values()) byStatus.put(status.name(), bookings.countByStatus(status));
        long outOfStock = products.findAll().stream().filter(p -> !p.isInStock()).count();
        return new DashboardStats(products.count(), outOfStock, services.count(), bookings.count(), reviews.countByApprovedFalse(), errors.count(), byStatus);
    }
}
