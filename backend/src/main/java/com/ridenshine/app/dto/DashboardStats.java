package com.ridenshine.app.dto;
import java.util.Map;
public record DashboardStats(long products, long outOfStockProducts, long services, long bookings, long pendingReviews, long trackedErrors, Map<String, Long> bookingsByStatus) {}
