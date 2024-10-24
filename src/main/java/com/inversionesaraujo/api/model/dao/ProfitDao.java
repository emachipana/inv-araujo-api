package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Profit;

public interface ProfitDao extends JpaRepository<Profit, Integer> {
  Profit findByMonth(String month);
}
