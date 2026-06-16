package com.ridenshine.app.controller;
import com.ridenshine.app.model.WorkshopService;
import com.ridenshine.app.service.WorkshopServiceManager;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/admin/services")
public class AdminServiceController {
    private final WorkshopServiceManager service;
    public AdminServiceController(WorkshopServiceManager service) { this.service = service; }
    @GetMapping public List<WorkshopService> all() { return service.allServices(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public WorkshopService create(@Valid @RequestBody WorkshopService s) { s.setId(null); return service.save(s); }
    @PutMapping("/{id}") public WorkshopService update(@PathVariable Long id, @Valid @RequestBody WorkshopService s) { return service.update(id, s); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
