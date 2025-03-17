package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Advance;

public interface AdvanceRepository extends JpaRepository<Advance, Long> {
    List<Advance> findByVitroOrderId(Long id);  
}
