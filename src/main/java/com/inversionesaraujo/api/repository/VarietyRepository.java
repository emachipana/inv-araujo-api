package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Variety;

public interface VarietyRepository extends JpaRepository<Variety, Integer> {}
