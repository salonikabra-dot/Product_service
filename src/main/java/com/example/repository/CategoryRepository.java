package com.example.repository;

import com.example.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findByParentId(String parentId);
    List<Category> findByIsDeletedFalse();
    Optional<Category> findByIdAndIsDeletedFalse(String id);


}
