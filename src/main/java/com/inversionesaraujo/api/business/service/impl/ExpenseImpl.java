package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IExpense;
import com.inversionesaraujo.api.model.Expense;
import com.inversionesaraujo.api.repository.ExpenseRepository;

@Service
public class ExpenseImpl implements IExpense {
    @Autowired
    private ExpenseRepository expenseRepo;

    @Transactional
    @Override
    public Expense save(Expense expense) {
        return expenseRepo.save(expense);
    }

    @Transactional(readOnly = true)
    @Override
    public Expense findById(Integer id) {
        return expenseRepo.findById(id).orElseThrow(() -> new DataAccessException("El gasto no existe") {});
    }

    @Transactional
    @Override
    public void delete(Expense expense) {
        expenseRepo.delete(expense);
    }
}
