package com.ridenshine.app.repository;
import com.ridenshine.app.model.ApplicationError;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ApplicationErrorRepository extends JpaRepository<ApplicationError, Long> {
    List<ApplicationError> findTop50ByOrderByOccurredAtDesc();
}
