/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.expensetracker.controller;

import com.project.expensetracker.model.Category;
import com.project.expensetracker.model.User;
import com.project.expensetracker.service.CategoryService;
import com.project.expensetracker.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{userId}/categories")
    public ResponseEntity<List<Category>> getAllCategories(@PathVariable Integer userId) {
        List<Category> categories = categoryService.getAllCategories(userId);
        return ResponseEntity.ok(categories);
    }
    
    @PostMapping("/users/{userId}/categories")
    public ResponseEntity<Void> addCategory(@RequestBody @Valid Category category, @PathVariable Integer userId) {
        // Fetch the existing user from the database
        User existingUser = userService.getUser(userId);

        // Set the existing user as the user for the category
        category.setUser(existingUser);

        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping("/users/{userId}/categories/{categoryId}")
    public ResponseEntity<Void> updateCategory(@RequestBody @Valid Category category, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        categoryService.updateCategory(category, userId, categoryId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/users/{userId}/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer userId, @PathVariable Integer categoryId) {
        categoryService.deleteCategory(userId, categoryId);
        return ResponseEntity.ok().build();
    }
}
