/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.expensetracker.controller;

import com.project.expensetracker.model.Accounts;
import com.project.expensetracker.service.AccountsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AccountsViewController {
    
    @Autowired
    private AccountsService accountsService;
    
    @GetMapping("/users/{userId}/accounts")
    public String getAllAccounts(@PathVariable Integer userId, Model model) {
        List<Accounts> accounts = accountsService.getAllAccounts(userId);
        model.addAttribute("accounts", accounts);
        return "pages/accounts";
    }
}
