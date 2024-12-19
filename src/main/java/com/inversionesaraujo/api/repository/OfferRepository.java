package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Integer> {}