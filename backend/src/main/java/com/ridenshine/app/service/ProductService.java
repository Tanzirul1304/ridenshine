package com.ridenshine.app.service;

import com.ridenshine.app.exception.ResourceNotFoundException;
import com.ridenshine.app.model.Product;
import com.ridenshine.app.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;
    public ProductService(ProductRepository repository) { this.repository = repository; }
    public List<Product> findAll(String search) {
        if (search == null || search.isBlank()) return repository.findAll();
        return repository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrBrandContainingIgnoreCase(search, search, search);
    }
    public Product findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")); }
    public Product save(Product product) {
        Product saved = repository.save(product);
        log.info("Product saved: id={}, name={}, stock={}", saved.getId(), saved.getName(), saved.getStockQuantity());
        return saved;
    }
    public Product update(Long id, Product input) {
        Product existing = findById(id);
        existing.setName(input.getName()); existing.setCategory(input.getCategory()); existing.setBrand(input.getBrand());
        existing.setPrice(input.getPrice()); existing.setStockQuantity(input.getStockQuantity());
        existing.setDescription(input.getDescription()); existing.setImageUrl(input.getImageUrl()); existing.setFeatured(input.isFeatured());
        return save(existing);
    }
    public void delete(Long id) { repository.delete(findById(id)); log.info("Product deleted: id={}", id); }
}
