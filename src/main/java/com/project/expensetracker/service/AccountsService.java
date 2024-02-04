/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.expensetracker.service;

import com.project.expensetracker.exception.ResourceNotFoundException;
import com.project.expensetracker.exception.UnauthorizedAccessException;
import com.project.expensetracker.model.Accounts;
import com.project.expensetracker.repository.AccountsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {
    
    @Autowired
    private AccountsRepository accountsRepository;
    
    public List<Accounts> getAllAccounts(Integer userId) {
        return new ArrayList<>(accountsRepository.findByUserUserId(userId));
    }
    
    public void addAccount(Accounts accounts) {       
        if (accounts == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accountsRepository.save(accounts);
    }
    
    
    public void updateAccount(Accounts accounts, Integer userId, Integer accountId) {
        Optional<Accounts> optionalAccount = accountsRepository.findById(accountId);
        
        if (optionalAccount.isPresent()) {
            Accounts existingAccount = optionalAccount.get();
            
            // Check if the account belongs to the specified user
            if (!existingAccount.getUser().getUserId().equals(userId)) {
                throw new UnauthorizedAccessException("You do not have permission to update this account");
            }
            
            existingAccount.setAccountType(accounts.getAccountType());
            existingAccount.setAccountName(accounts.getAccountName());
            existingAccount.setBalance(accounts.getBalance());
            accountsRepository.save(existingAccount);
        }
        else {
            throw new ResourceNotFoundException("Account with ID: " + accountId + " not found");
        }
    }
    
    public void deleteAccount(Integer userId, Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        
        Optional<Accounts> optionalAccount = accountsRepository.findById(accountId);
        
        if (optionalAccount.isPresent()) {
            
            Accounts existingAccount = optionalAccount.get();
            
            // Check if the account belongs to the specified user
            if (!existingAccount.getUser().getUserId().equals(userId)) {
                throw new UnauthorizedAccessException("You do not have permission to delete this account");
            }
            accountsRepository.deleteById(accountId);
        }
        else {
            throw new ResourceNotFoundException("Account with ID: " + accountId + " not found");
        }
    }
}