package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {}
