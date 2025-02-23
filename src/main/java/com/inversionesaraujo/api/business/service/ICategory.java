package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.CategoryDTO;

public interface ICategory {
    List<CategoryDTO> listAll();

    List<CategoryDTO> getSubCategories(Long categoryId);


    CategoryDTO save(CategoryDTO category);

    CategoryDTO findById(Long id);

    void delete(Long id);
}
