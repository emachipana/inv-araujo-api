package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryIsNull();

    List<Category> findByCategoryId(Long categoryId);

    Integer countByCategoryId(Long parentId);
}
