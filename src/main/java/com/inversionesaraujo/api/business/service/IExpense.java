package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.Expense;

public interface IExpense {
    Expense save(Expense expense);

    Expense findById(Integer id);

    void delete(Expense expense);
}
