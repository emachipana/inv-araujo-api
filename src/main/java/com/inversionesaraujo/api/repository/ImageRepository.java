package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {}
