package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {}
