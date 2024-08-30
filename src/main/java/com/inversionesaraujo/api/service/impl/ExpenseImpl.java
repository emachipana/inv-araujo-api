package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ExpenseDao;
import com.inversionesaraujo.api.model.entity.Expense;
import com.inversionesaraujo.api.service.IExpense;

@Service
public class ExpenseImpl implements IExpense {
    @Autowired
    private ExpenseDao expenseDao;

    @Transactional(readOnly = true)
    @Override
    public List<Expense> listAll() {
        return expenseDao.findAll();
    }

    @Transactional
    @Override
    public Expense save(Expense expense) {
        return expenseDao.save(expense);
    }

    @Transactional(readOnly = true)
    @Override
    public Expense findById(Integer id) {
        return expenseDao.findById(id).orElseThrow(() -> new DataAccessException("El gasto no existe") {});
    }

    @Transactional
    @Override
    public void delete(Expense expense) {
        expenseDao.delete(expense);
    }
}
