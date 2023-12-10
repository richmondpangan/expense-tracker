/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.expensetracker.controller;

import com.project.expensetracker.model.Accounts;
import com.project.expensetracker.model.User;
import com.project.expensetracker.service.AccountsService;
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
public class AccountsController {
    
    @Autowired
    private AccountsService accountsService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<Accounts>> getAllAccounts(@PathVariable Integer userId) {
        List<Accounts> accounts = accountsService.getAllAccounts(userId);
        return ResponseEntity.ok(accounts);
    }
    
    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<Void> addAccount(@RequestBody @Valid Accounts accounts, @PathVariable Integer userId) {
        // Fetch the existing user from the database
        User existingUser = userService.getUser(userId);

        // Set the existing user as the user for the account
        accounts.setUser(existingUser);

        accountsService.addAccount(accounts);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping("/users/{userId}/accounts/{accountId}")
    public ResponseEntity<Void> updateAccount(@RequestBody @Valid Accounts accounts, @PathVariable Integer userId, @PathVariable Integer accountId) {
        accountsService.updateAccount(accounts, userId, accountId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/users/{userId}/accounts/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer userId, @PathVariable Integer accountId) {
        accountsService.deleteAccount(userId, accountId);
        return ResponseEntity.ok().build();
    }
}
