package com.example.service;

import com.example.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    List<Product> getAllByTenant(String tenantId);
    Product getById(String id, String tenantId);
    Product update(String id, Product updatedProduct, String tenantId);
    void delete(String id, String tenantId);

}
