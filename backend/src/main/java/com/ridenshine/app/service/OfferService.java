package com.ridenshine.app.service;

import com.ridenshine.app.exception.ResourceNotFoundException;
import com.ridenshine.app.model.Offer;
import com.ridenshine.app.repository.OfferRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfferService {
    private final OfferRepository repository;
    public OfferService(OfferRepository repository) { this.repository = repository; }
    public List<Offer> active() { return repository.findByActiveTrueOrderByValidUntilAsc(); }
    public List<Offer> all() { return repository.findAll(); }
    public Offer save(Offer o) { return repository.save(o); }
    public Offer update(Long id, Offer input) {
        Offer o = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Offer not found"));
        o.setTitle(input.getTitle()); o.setDescription(input.getDescription()); o.setDiscountPercent(input.getDiscountPercent());
        o.setValidUntil(input.getValidUntil()); o.setActive(input.isActive()); return repository.save(o);
    }
    public void delete(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Offer not found"); repository.deleteById(id); }
}
