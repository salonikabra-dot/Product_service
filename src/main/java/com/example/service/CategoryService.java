package com.example.service;

import com.example.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> getAll();
    List<Category> getSubCategories(String parentId);
    Category getById(String id);
    Category updateCategory(String id, Category updatedCategory);
    void deleteCategory(String id);


}
