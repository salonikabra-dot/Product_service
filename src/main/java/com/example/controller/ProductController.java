package com.example.controller;//import com.example.dto.ProductResponseDto;
import com.example.DTO.ProductResponseDTO;
import com.example.model.Product;
import com.example.service.ProductService;
import com.example.config.JwtUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    private String extractTenantId(String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        return jwtUtil.extractTenantId(token);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> create(@RequestBody Product product,
                                                     @RequestHeader("Authorization") String authHeader) {
        String tenantId = extractTenantId(authHeader);
        product.setTenantId(tenantId);
        Product saved = productService.create(product);
        return ResponseEntity.ok(productService.convertToDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(@RequestHeader("Authorization") String authHeader) {
        String tenantId = extractTenantId(authHeader);
        List<Product> products = productService.getAllByTenant(tenantId);
        List<ProductResponseDTO> response = products.stream()
                .map(productService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable String id,
                                                      @RequestHeader("Authorization") String authHeader) {
        String tenantId = extractTenantId(authHeader);
        Product product = productService.getById(id, tenantId);
        return ResponseEntity.ok(productService.convertToDto(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable String id,
                                                     @RequestBody Product product,
                                                     @RequestHeader("Authorization") String authHeader) {
        String tenantId = extractTenantId(authHeader);
        Product updated = productService.update(id, product, tenantId);
        return ResponseEntity.ok(productService.convertToDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String id,
                                         @RequestHeader("Authorization") String authHeader) {
        String tenantId = extractTenantId(authHeader);
        productService.delete(id, tenantId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
