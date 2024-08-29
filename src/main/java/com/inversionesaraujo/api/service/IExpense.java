package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Expense;

public interface IExpense {
    List<Expense> listAll();

    Expense save(Expense expense);

    Expense findById(Integer id);

    void delete(Expense expense);

    boolean ifExists(Integer id);
}
