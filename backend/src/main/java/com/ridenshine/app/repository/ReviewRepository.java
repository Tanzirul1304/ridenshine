package com.ridenshine.app.repository;
import com.ridenshine.app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByApprovedTrueOrderByCreatedAtDesc();
    List<Review> findAllByOrderByCreatedAtDesc();
    long countByApprovedFalse();
}
