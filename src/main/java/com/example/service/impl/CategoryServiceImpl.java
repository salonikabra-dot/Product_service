package com.example.service.impl;

import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }



    @Override
    public List<Category> getAll() {
        return categoryRepository.findByIsDeletedFalse();
    }


    @Override
    public List<Category> getSubCategories(String parentId) {
        return categoryRepository.findByParentId(parentId);
    }


@Override
public Category getById(String id) {
    return categoryRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
}


    @Override
    public Category updateCategory(String id, Category updatedCategory) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existing.setName(updatedCategory.getName());
        existing.setSlug(updatedCategory.getSlug());
        existing.setDescription(updatedCategory.getDescription());
        existing.setParentId(updatedCategory.getParentId());

        return categoryRepository.save(existing);
    }


@Override
public void deleteCategory(String id) {
    Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    category.setIsDeleted(true);
    categoryRepository.save(category);
}



}
