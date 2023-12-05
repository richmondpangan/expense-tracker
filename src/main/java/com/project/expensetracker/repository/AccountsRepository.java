/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.expensetracker.repository;

import com.project.expensetracker.model.Accounts;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface AccountsRepository extends CrudRepository<Accounts, Integer>{
    public List<Accounts> findByUserUserId(Integer userId);
}
