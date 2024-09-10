package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.OfferProduct;

public interface OfferProductDao extends JpaRepository<OfferProduct, Integer> {}
