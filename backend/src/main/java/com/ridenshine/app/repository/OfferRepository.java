package com.ridenshine.app.repository;
import com.ridenshine.app.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByActiveTrueOrderByValidUntilAsc();
}
