package com.ridenshine.app.service;

import com.ridenshine.app.exception.ResourceNotFoundException;
import com.ridenshine.app.model.Review;
import com.ridenshine.app.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository repository;
    public ReviewService(ReviewRepository repository) { this.repository = repository; }
    public List<Review> approved() { return repository.findByApprovedTrueOrderByCreatedAtDesc(); }
    public List<Review> all() { return repository.findAllByOrderByCreatedAtDesc(); }
    public Review submit(Review review) { review.setApproved(false); Review saved = repository.save(review); log.info("Review submitted for moderation: id={}", saved.getId()); return saved; }
    public Review approve(Long id) { Review r = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found")); r.setApproved(true); return repository.save(r); }
    public void delete(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Review not found"); repository.deleteById(id); }
}
