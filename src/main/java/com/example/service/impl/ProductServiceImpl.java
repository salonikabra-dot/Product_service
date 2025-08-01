package com.example.service.impl;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllByTenant(String tenantId) {
        return productRepository.findByTenantId(tenantId);
    }


    @Override
    public Product getById(String id, String tenantId) {
        return productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product update(String id, Product updatedProduct, String tenantId) {
        Product existing = productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updatedProduct.getName());
        existing.setSlug(updatedProduct.getSlug());
        existing.setDescription(updatedProduct.getDescription());
        existing.setBrand(updatedProduct.getBrand());
        existing.setCategoryId(updatedProduct.getCategoryId());
        existing.setSubCategoryId(updatedProduct.getSubCategoryId());
        existing.setPrice(updatedProduct.getPrice());
        existing.setFinalPrice(updatedProduct.getFinalPrice());
        existing.setDiscount(updatedProduct.getDiscount());
        existing.setStock(updatedProduct.getStock());
        existing.setSku(updatedProduct.getSku());
        existing.setImages(updatedProduct.getImages());
        existing.setAttributes(updatedProduct.getAttributes());

        return productRepository.save(existing);
    }

    @Override
    public void delete(String id, String tenantId) {
        Product product = productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

}
