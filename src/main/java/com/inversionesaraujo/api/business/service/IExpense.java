package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.ExpenseDTO;

public interface IExpense {
    ExpenseDTO save(ExpenseDTO expense);

    ExpenseDTO findById(Long id);

    void delete(Long id);
}
