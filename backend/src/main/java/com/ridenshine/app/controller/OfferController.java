package com.ridenshine.app.controller;
import com.ridenshine.app.model.Offer;
import com.ridenshine.app.service.OfferService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/offers")
public class OfferController {
    private final OfferService service;
    public OfferController(OfferService service) { this.service = service; }
    @GetMapping public List<Offer> active() { return service.active(); }
}
