package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Embedding;

public interface EmbeddingRepository extends JpaRepository<Embedding, Long> {}
