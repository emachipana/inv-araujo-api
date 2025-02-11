package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.ExpenseDTO;
import com.inversionesaraujo.api.business.service.IExpense;
import com.inversionesaraujo.api.model.Expense;
import com.inversionesaraujo.api.repository.ExpenseRepository;

@Service
public class ExpenseImpl implements IExpense {
    @Autowired
    private ExpenseRepository expenseRepo;

    @Transactional
    @Override
    public ExpenseDTO save(ExpenseDTO expense) {
        Expense expenseSaved = expenseRepo.save(ExpenseDTO.toEntity(expense));

        return ExpenseDTO.toDTO(expenseSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public ExpenseDTO findById(Long id) {
        Expense expense = expenseRepo.findById(id).orElseThrow(() -> new DataAccessException("El gasto no existe") {});

        return ExpenseDTO.toDTO(expense);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        expenseRepo.deleteById(id);
    }
}
