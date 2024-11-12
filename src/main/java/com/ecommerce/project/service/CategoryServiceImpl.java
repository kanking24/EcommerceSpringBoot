package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
//    private List<Category> categories = new ArrayList<>();
    private long nextId;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(nextId++);
        categoryRepository.save(category);
        return "Created Successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.stream()
                                       .filter(c -> c.getCategoryId().equals(categoryId))
                                        .findFirst()
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        categoryRepository.delete(category);
        return "Category with category id : " + categoryId + " deleted successfully." ;
    }

    @Override
    public String updateCategory(Category category) {
       Optional<Category> optionalCategory = categoryRepository.findById(category.getCategoryId());

        if(optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryId(category.getCategoryId());
            existingCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(existingCategory);
            return "Updated the values";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This value was not found");
        }
    }
}
