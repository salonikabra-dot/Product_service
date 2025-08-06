package com.example.service;

import com.example.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> getAll();
    List<Category> getSubCategories(String parentId);
    Category getById(String id);
    Category updateCategory(String id, Category updatedCategory, MultipartFile imageFile);
    void deleteCategory(String id);
}
