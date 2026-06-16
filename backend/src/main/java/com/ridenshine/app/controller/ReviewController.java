package com.ridenshine.app.controller;
import com.ridenshine.app.model.Review;
import com.ridenshine.app.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService service;
    public ReviewController(ReviewService service) { this.service = service; }
    @GetMapping public List<Review> approved() { return service.approved(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Review submit(@Valid @RequestBody Review review) { return service.submit(review); }
}
