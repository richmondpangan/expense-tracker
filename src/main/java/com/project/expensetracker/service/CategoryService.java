/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.expensetracker.service;

import com.project.expensetracker.exception.ResourceNotFoundException;
import com.project.expensetracker.exception.UnauthorizedAccessException;
import com.project.expensetracker.model.Category;
import com.project.expensetracker.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> getAllCategories(Integer userId) {
        return new ArrayList<>(categoryRepository.findByUserUserId(userId));
    }
    
    public void addCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        categoryRepository.save(category);
    }
    
    public void updateCategory(Category category, Integer userId, Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            
            // Check if the category belongs to the specified user
            if (!existingCategory.getUser().getUserId().equals(userId)) {
                throw new UnauthorizedAccessException("You do not have permission to update this category");
            }
            
            existingCategory.setCategoryType(category.getCategoryType());
            existingCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(existingCategory);
        }
        else {
            throw new ResourceNotFoundException("Category with ID: " + categoryId + " not found");
        }
    }
    
    public void deleteCategory(Integer userId, Integer categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            
            // Check if the category belongs to the specified user
            if (!existingCategory.getUser().getUserId().equals(userId)) {
                throw new UnauthorizedAccessException("You do not have permission to delete this category");
            }
            categoryRepository.deleteById(categoryId);
        }
        else {
            throw new ResourceNotFoundException("Category with ID: " + categoryId + " not found");
        }
    }
    
}
