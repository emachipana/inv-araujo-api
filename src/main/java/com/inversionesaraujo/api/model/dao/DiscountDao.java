package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Discount;

public interface DiscountDao extends JpaRepository<Discount, Integer> {}
