package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.OrderVariety;

public interface OrderVarietyRepository extends JpaRepository<OrderVariety, Long> {
  List<OrderVariety> findByVitroOrderId(Long orderId);
}
