package com.ridenshine.app.controller;
import com.ridenshine.app.model.Offer;
import com.ridenshine.app.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/admin/offers")
public class AdminOfferController {
    private final OfferService service;
    public AdminOfferController(OfferService service) { this.service = service; }
    @GetMapping public List<Offer> all() { return service.all(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Offer create(@Valid @RequestBody Offer o) { o.setId(null); return service.save(o); }
    @PutMapping("/{id}") public Offer update(@PathVariable Long id, @Valid @RequestBody Offer o) { return service.update(id, o); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
