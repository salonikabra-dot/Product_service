package com.example.repository;

import com.example.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByTenantId(String tenantId);
    Optional<Product> findByIdAndTenantId(String id, String tenantId);
    List<Product> findByTenantIdAndIsDeletedFalse(String tenantId);
    Optional<Product> findByIdAndTenantIdAndIsDeletedFalse(String id, String tenantId);List<Product> findByTenantIdAndCategoryId(String tenantId, String categoryId);
   // List<Product> getAllByCategoryAndTenant(String categoryId, String tenantId);
   List<Product> findByCategoryIdAndTenantId(String categoryId, String tenantId);






}
