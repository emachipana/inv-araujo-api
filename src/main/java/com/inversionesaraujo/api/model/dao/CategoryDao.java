package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {}
