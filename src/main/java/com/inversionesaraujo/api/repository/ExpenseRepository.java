package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {}
