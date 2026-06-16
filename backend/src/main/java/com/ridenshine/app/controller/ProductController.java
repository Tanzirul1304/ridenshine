package com.ridenshine.app.controller;
import com.ridenshine.app.model.Product;
import com.ridenshine.app.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }
    @GetMapping public List<Product> all(@RequestParam(required = false) String search) { return service.findAll(search); }
    @GetMapping("/{id}") public Product one(@PathVariable Long id) { return service.findById(id); }
}
