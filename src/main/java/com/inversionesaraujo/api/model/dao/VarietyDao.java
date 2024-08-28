package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Variety;

public interface VarietyDao extends JpaRepository<Variety, Integer> {}
