package com.example.service;

import com.example.DTO.ProductResponseDTO;
import com.example.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product create(Product product,MultipartFile imageFile);
    List<Product> getAllByTenant(String tenantId);
    Product getById(String id, String tenantId);
    void delete(String id, String tenantId);
    ProductResponseDTO convertToDto(Product product);
    Product update(String id, Product updatedProduct, String tenantId, MultipartFile imageFile);




}
