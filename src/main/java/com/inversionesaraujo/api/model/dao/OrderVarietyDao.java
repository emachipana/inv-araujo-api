package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.OrderVariety;

public interface OrderVarietyDao extends JpaRepository<OrderVariety, Integer> {}