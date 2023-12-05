/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.expensetracker.service;

import com.project.expensetracker.model.User;
import com.project.expensetracker.repository.UserRepository;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getUser(Integer userId) {
        return userRepository.findById(userId).get();
    }
    
    public void registerUser(User user) {
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }
}
