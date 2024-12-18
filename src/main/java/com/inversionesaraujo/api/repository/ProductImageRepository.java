package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {}
