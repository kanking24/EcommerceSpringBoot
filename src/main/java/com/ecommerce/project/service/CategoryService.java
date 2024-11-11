package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    String createCategory(Category category);

    String deleteCategory(Long categoryId);

    String updateCategory(Category category);
}