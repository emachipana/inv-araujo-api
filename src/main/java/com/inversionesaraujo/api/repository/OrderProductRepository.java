package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {}
