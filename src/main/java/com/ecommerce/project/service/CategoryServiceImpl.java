package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private List<Category> categories = new ArrayList<>();
    private long nextId;
    @Override
    public List<Category> getAllCategories() {
        if(categories.size() > 0){
            return categories;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
        return "Created Successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                                        .filter(c -> c.getCategoryId().equals(categoryId))
                                        .findFirst()
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        categories.remove(category);
        return "Category with category id : " + categoryId + " deleted successfully." ;
    }

    @Override
    public String updateCategory(Category category) {
        Optional<Category> optionalCategory = categories.stream().filter(c -> c.getCategoryId().equals(category.getCategoryId()))
                .findFirst();

        if(optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryId(category.getCategoryId());
            existingCategory.setCategoryName(category.getCategoryName());
            return "Updated the values";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This value was not found");
        }
    }
}
