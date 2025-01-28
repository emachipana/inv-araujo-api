package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {}
