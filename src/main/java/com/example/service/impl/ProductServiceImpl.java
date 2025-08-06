package com.example.service.impl;

import com.example.DTO.ProductResponseDTO;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.example.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final ZoneId IST = ZoneId.of("Asia/Kolkata");


    @Override
    public Product create(Product product) {

        product.setCreatedAt(ZonedDateTime.now(IST).toInstant());
        product.setUpdatedAt(ZonedDateTime.now(IST).toInstant());

        return productRepository.save(product);
    }



    @Override
    public List<Product> getAllByTenant(String tenantId) {
        return productRepository.findByTenantIdAndIsDeletedFalse(tenantId);
    }





    @Override
    public Product getById(String id, String tenantId) {
        return productRepository.findByIdAndTenantIdAndIsDeletedFalse(id, tenantId)
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
    Product product = productRepository.findByIdAndTenantIdAndIsDeletedFalse(id, tenantId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    product.setIsDeleted(true);
    product.setUpdatedAt(ZonedDateTime.now(IST).toInstant());
    productRepository.save(product);
}



    public ProductResponseDTO convertToDto(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .tenantId(product.getTenantId())
                .name(product.getName())
                .slug(product.getSlug())
                .description(product.getDescription())
                .brand(product.getBrand())
                .categoryId(product.getCategoryId())
                .subCategoryId(product.getSubCategoryId())
                .price(product.getPrice())
                .finalPrice(product.getFinalPrice())
                .stock(product.getStock())
                .sku(product.getSku())
                .images(product.getImages())
                .attributes(product.getAttributes())
                .tags(product.getTags())
                .isActive(product.getIsActive())
                .createdAt(DateTimeUtils.toISTString(product.getCreatedAt()))
                .updatedAt(DateTimeUtils.toISTString(product.getUpdatedAt()))
                .build();
    }


}
