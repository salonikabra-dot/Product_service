
package com.example.service.impl;

import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private final String UPLOAD_DIR = "uploads/categories/";

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
    public Category updateCategory(String id, Category updatedCategory, MultipartFile imageFile) {
        Category existing = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existing.setName(updatedCategory.getName());
        existing.setSlug(updatedCategory.getSlug());
        existing.setDescription(updatedCategory.getDescription());
        existing.setParentId(updatedCategory.getParentId());
        existing.setIsActive(updatedCategory.getIsActive());

        // Handle image update
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, imageFile.getBytes());

                // Save the image path relative to the server
                existing.setImageUrl("/uploads/categories/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

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

