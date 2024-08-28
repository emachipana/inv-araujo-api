package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.LandingOfferProduct;

public interface LandingOfferProductDao extends JpaRepository<LandingOfferProduct, Integer> {}
