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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountsViewController {
    
    @Autowired
    private AccountsService accountsService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{userId}/accounts")
    public String getAllAccounts(@PathVariable Integer userId, Model model) {
        List<Accounts> accounts = accountsService.getAllAccounts(userId);
        model.addAttribute("accounts", accounts);
        model.addAttribute("userId", userId);
        return "pages/accounts";
    }
    
    @GetMapping("users/{userId}/accounts/fetchUpdate")
    @ResponseBody
    public List<Accounts> getUpdatedAccounts(@PathVariable Integer userId, Model model) {
        List<Accounts> updatedAccounts = accountsService.getAllAccounts(userId);
        model.addAttribute("userId", userId);
        return updatedAccounts;
    }
    
    @GetMapping("/users/{userId}/accounts/add")
    public String showAddAccountModal(@PathVariable Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "modals/addAccountModal";
    }
    
    @PostMapping("/users/{userId}/accounts/add")
    public ResponseEntity<String> addAccount(@RequestBody @Valid Accounts accounts, @PathVariable Integer userId) {
        // Fetch the existing user from the database
        User existingUser = userService.getUser(userId);

        // Set the existing user as the user for the account
        accounts.setUser(existingUser);
        
        accountsService.addAccount(accounts);
        return ResponseEntity.ok("Account saved successfully");
    }
    
    @GetMapping("/users/{userId}/accounts/{accountId}/edit")
    public String showEditAccountModal(@PathVariable Integer userId, @PathVariable Integer accountId, Model model) {
        List<Accounts> accounts = accountsService.getAllAccounts(userId);
        model.addAttribute("accounts", accounts);
        return "modals/editAccountModal";
    }
    
    @PutMapping("/users/{userId}/accounts/{accountId}/edit")
    public ResponseEntity<String> updateAccount(@RequestBody @Valid Accounts accounts, @PathVariable Integer userId, @PathVariable Integer accountId) {
        accountsService.updateAccount(accounts, userId, accountId);
        return ResponseEntity.ok("Account edited successfully");
    }
    
    @GetMapping("/users/{userId}/accounts/{accountId}")
    public String showDeleteAccountModal(@PathVariable Integer userId, @PathVariable Integer accountId) {
        return "modals/deleteAccountModal";
    }
    
    @DeleteMapping("/users/{userId}/accounts/{accountId}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer userId, @PathVariable Integer accountId) {
        accountsService.deleteAccount(userId, accountId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
