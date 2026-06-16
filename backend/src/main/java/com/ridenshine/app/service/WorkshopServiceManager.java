package com.ridenshine.app.service;

import com.ridenshine.app.exception.ResourceNotFoundException;
import com.ridenshine.app.model.WorkshopService;
import com.ridenshine.app.repository.WorkshopServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkshopServiceManager {
    private static final Logger log = LoggerFactory.getLogger(WorkshopServiceManager.class);
    private final WorkshopServiceRepository repository;
    public WorkshopServiceManager(WorkshopServiceRepository repository) { this.repository = repository; }
    public List<WorkshopService> activeServices() { return repository.findByActiveTrueOrderByNameAsc(); }
    public List<WorkshopService> allServices() { return repository.findAll(); }
    public WorkshopService findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found")); }
    public WorkshopService save(WorkshopService service) { WorkshopService saved = repository.save(service); log.info("Workshop service saved: id={}, name={}", saved.getId(), saved.getName()); return saved; }
    public WorkshopService update(Long id, WorkshopService input) {
        WorkshopService existing = findById(id);
        existing.setName(input.getName()); existing.setDescription(input.getDescription()); existing.setPrice(input.getPrice());
        existing.setDurationMinutes(input.getDurationMinutes()); existing.setActive(input.isActive());
        return save(existing);
    }
    public void delete(Long id) { repository.delete(findById(id)); }
}
