package com.ridenshine.app.config;

import com.ridenshine.app.model.*;
import com.ridenshine.app.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeeder {
    @Bean CommandLineRunner seed(ProductRepository products, WorkshopServiceRepository services, OfferRepository offers) {
        return args -> {
            if (products.count() == 0) products.saveAll(List.of(
                product("Motocare Motorcycle Cover", "Accessories", "Motocare Bangladesh", 1250, 8, "Weather-resistant motorcycle cover.", true),
                product("10W-40 Synthetic Engine Oil", "Engine Oil", "Demo Brand", 950, 15, "Suitable for regular motorcycle maintenance.", true),
                product("NGK Spark Plug", "Engine Parts", "NGK", 450, 20, "Common replacement spark plug; confirm model compatibility.", false),
                product("Front Disc Brake Pad", "Brakes", "Demo Brand", 850, 7, "Replacement brake pad; fitting available in shop.", true),
                product("Motorcycle Air Filter", "Filters", "Demo Brand", 600, 0, "Demo stock-out item for showing availability control.", false),
                product("Chain and Sprocket Set", "Drive System", "Demo Brand", 3200, 5, "Chain and sprocket replacement set.", true),
                product("LED Headlight Bulb", "Electrical", "Demo Brand", 1100, 10, "Bright LED replacement bulb; compatibility check required.", false),
                product("Chain Cleaner Spray", "Maintenance", "Demo Brand", 650, 12, "Cleaner for motorcycle chains and sprockets.", false),
                product("Chain Lubricant", "Maintenance", "Demo Brand", 700, 12, "Protective chain lubricant for regular care.", false),
                product("Helmet Visor", "Rider Accessories", "Demo Brand", 800, 6, "Clear replacement visor; model compatibility varies.", false)
            ));
            if (services.count() == 0) services.saveAll(List.of(
                workshop("Regular Motorcycle Servicing", "Routine inspection, adjustment and basic maintenance.", 800, 60),
                workshop("Engine Oil Change", "Oil replacement and basic leak inspection. Oil price may be separate.", 200, 20),
                workshop("Bike Wash", "Exterior wash and basic finishing.", 250, 30),
                workshop("Chain Cleaning and Adjustment", "Chain cleaning, lubrication, tension check and adjustment.", 350, 30),
                workshop("Brake Servicing", "Brake inspection, cleaning and adjustment. Parts are charged separately.", 500, 45),
                workshop("Electrical Diagnosis", "Battery, lighting, wiring and electrical fault inspection.", 600, 60),
                workshop("Full Motorcycle Inspection", "Multi-point safety and condition inspection.", 1000, 90)
            ));
            if (offers.count() == 0) {
                Offer offer = new Offer(); offer.setTitle("Online Service Booking");
                offer.setDescription("Reserve a preferred service date online, then wait for confirmation from RideNShine.");
                offer.setDiscountPercent(0); offer.setValidUntil(LocalDate.now().plusMonths(6)); offer.setActive(true); offers.save(offer);
            }
        };
    }
    private Product product(String name, String category, String brand, int price, int stock, String description, boolean featured) {
        Product p = new Product(); p.setName(name); p.setCategory(category); p.setBrand(brand); p.setPrice(BigDecimal.valueOf(price));
        p.setStockQuantity(stock); p.setDescription(description); p.setFeatured(featured); return p;
    }
    private WorkshopService workshop(String name, String description, int price, int duration) {
        WorkshopService s = new WorkshopService(); s.setName(name); s.setDescription(description); s.setPrice(BigDecimal.valueOf(price));
        s.setDurationMinutes(duration); s.setActive(true); return s;
    }
}
