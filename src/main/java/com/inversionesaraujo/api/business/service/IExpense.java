package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.ExpenseDTO;

public interface IExpense {
    ExpenseDTO save(ExpenseDTO expense);

    List<ExpenseDTO> findAllByProfitId(Long profitId);

    ExpenseDTO findById(Long id);

    void delete(Long id);
}
