package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Profit;

public interface ProfitRepository extends JpaRepository<Profit, Long> {
    Profit findByMonth(String month);
}
