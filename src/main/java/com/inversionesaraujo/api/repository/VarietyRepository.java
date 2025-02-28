package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Variety;

public interface VarietyRepository extends JpaRepository<Variety, Long> {
    List<Variety> findByTuberId(Long tuberId);
}
