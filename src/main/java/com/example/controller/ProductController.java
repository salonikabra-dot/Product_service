package com.example.controller;//import com.example.dto.ProductResponseDto;
import com.example.DTO.ProductResponseDTO;
import com.example.model.Product;
import com.example.service.ProductService;
import com.example.config.JwtUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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




@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@PreAuthorize("hasAuthority('ADMIN')")
public ResponseEntity<ProductResponseDTO> create(
        @RequestPart("product") Product product,
        @RequestPart(value = "image", required = false) MultipartFile imageFile,
        @RequestHeader("Authorization") String authHeader) {

    String tenantId = extractTenantId(authHeader);
    product.setTenantId(tenantId);
    Product saved = productService.create(product, imageFile);
    return ResponseEntity.ok(productService.convertToDto(saved));
}






    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        List<Product> products = productService.getAllPublic(); // fetch all products, no tenant/role filtering
        List<ProductResponseDTO> response = products.stream()
                .map(productService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }








    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable String id,
                                                      @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Product product;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String tenantId = extractTenantId(authHeader);
            product = productService.getById(id, tenantId);
        } else {
            product = productService.getByIdPublic(id);
        }
        return ResponseEntity.ok(productService.convertToDto(product));
    }


@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@PreAuthorize("hasAuthority('ADMIN')")
public ResponseEntity<ProductResponseDTO> update(
        @PathVariable String id,
        @RequestPart("product") Product updatedProduct,
        @RequestPart(value = "image", required = false) MultipartFile imageFile,
        @RequestHeader("Authorization") String authHeader) {

    String tenantId = extractTenantId(authHeader);
    Product updated = productService.update(id, updatedProduct, tenantId, imageFile);
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

    @PutMapping("/{id}/discount")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> setDiscount(
            @PathVariable String id,
            @RequestParam("type") String type,
            @RequestParam("value") Double value,
            @RequestHeader("Authorization") String authHeader) {

        String tenantId = extractTenantId(authHeader);
        Product product = productService.getById(id, tenantId);

        product.setDiscount(Product.DiscountInfo.builder()
                .type("PERCENTAGE")
                .value(value)
                .build());

        Product updated = productService.update(id, product, tenantId, null);
        return ResponseEntity.ok(productService.convertToDto(updated));
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(
            @PathVariable String categoryId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        List<Product> products;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String tenantId = extractTenantId(authHeader);
            products = productService.getByCategory(categoryId, tenantId);
        } else {
            products = productService.getByCategoryPublic(categoryId);
        }
        List<ProductResponseDTO> response = products.stream()
                .map(productService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


}
