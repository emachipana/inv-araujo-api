package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.OfferProduct;

public interface OfferProductRepository extends JpaRepository<OfferProduct, Long> {
  List<OfferProduct> findByOfferId(Long id);
}
