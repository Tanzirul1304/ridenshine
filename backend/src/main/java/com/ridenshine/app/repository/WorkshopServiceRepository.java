package com.ridenshine.app.repository;
import com.ridenshine.app.model.WorkshopService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface WorkshopServiceRepository extends JpaRepository<WorkshopService, Long> {
    List<WorkshopService> findByActiveTrueOrderByNameAsc();
}
