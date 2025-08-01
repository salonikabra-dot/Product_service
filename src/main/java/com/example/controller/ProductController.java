package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product,
                                          @RequestHeader("X-Tenant-ID") String tenantId) {
        product.setTenantId(tenantId);
        return ResponseEntity.ok(productService.create(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(productService.getAllByTenant(tenantId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id,
                                           @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(productService.getById(id, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id,
                                          @RequestBody Product product,
                                          @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(productService.update(id, product, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id,
                                         @RequestHeader("X-Tenant-ID") String tenantId) {
        productService.delete(id, tenantId);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
