package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Category;

public interface ICategory {
    List<Category> listParentCategories();

    Category save(Category category);

    Category findById(Integer id);

    void delete(Category category);
}
