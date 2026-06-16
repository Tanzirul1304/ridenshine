package com.ridenshine.app.controller;
import com.ridenshine.app.model.Review;
import com.ridenshine.app.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/admin/reviews")
public class AdminReviewController {
    private final ReviewService service;
    public AdminReviewController(ReviewService service) { this.service = service; }
    @GetMapping public List<Review> all() { return service.all(); }
    @PatchMapping("/{id}/approve") public Review approve(@PathVariable Long id) { return service.approve(id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
