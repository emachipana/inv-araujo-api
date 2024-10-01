package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Advance;

public interface AdvanceDao extends JpaRepository<Advance, Integer> {}
