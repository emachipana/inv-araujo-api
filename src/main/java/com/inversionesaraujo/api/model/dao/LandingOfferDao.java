package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.LandingOffer;

public interface LandingOfferDao extends JpaRepository<LandingOffer, Integer> {}
