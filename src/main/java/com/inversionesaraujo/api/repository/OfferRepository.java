package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
  List<Offer> findByIsUsed(Boolean isUsed);
}
