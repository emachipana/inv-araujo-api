package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.Expense;

public interface IExpense {
    Expense save(Expense expense);

    Expense findById(Integer id);

    void delete(Expense expense);
}
