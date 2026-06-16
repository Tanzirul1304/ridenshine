package com.ridenshine.app.controller;
import com.ridenshine.app.model.WorkshopService;
import com.ridenshine.app.service.WorkshopServiceManager;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/services")
public class WorkshopServiceController {
    private final WorkshopServiceManager service;
    public WorkshopServiceController(WorkshopServiceManager service) { this.service = service; }
    @GetMapping public List<WorkshopService> all() { return service.activeServices(); }
}
