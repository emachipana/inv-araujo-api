package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Advance;

public interface AdvanceRepository extends JpaRepository<Advance, Long> {}
