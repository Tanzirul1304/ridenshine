package com.ridenshine.app.repository;
import com.ridenshine.app.model.Booking;
import com.ridenshine.app.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByReferenceCodeIgnoreCase(String referenceCode);
    long countByStatus(BookingStatus status);
    List<Booking> findAllByOrderByCreatedAtDesc();
}
