package com.ridenshine.app.controller;
import com.ridenshine.app.dto.DashboardStats;
import com.ridenshine.app.model.ApplicationError;
import com.ridenshine.app.repository.ApplicationErrorRepository;
import com.ridenshine.app.service.DashboardService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/admin")
public class AdminMonitoringController {
    private final DashboardService dashboard; private final ApplicationErrorRepository errors;
    public AdminMonitoringController(DashboardService dashboard, ApplicationErrorRepository errors) { this.dashboard = dashboard; this.errors = errors; }
    @GetMapping("/stats") public DashboardStats stats() { return dashboard.stats(); }
    @GetMapping("/errors") public List<ApplicationError> errors() { return errors.findTop50ByOrderByOccurredAtDesc(); }
    @GetMapping("/ping") public java.util.Map<String, String> ping() { return java.util.Map.of("status", "authenticated"); }
}
