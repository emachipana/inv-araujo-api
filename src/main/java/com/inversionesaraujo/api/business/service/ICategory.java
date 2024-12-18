package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.model.Category;

public interface ICategory {
    List<Category> listAll();

    Category save(Category category);

    Category findById(Integer id);

    void delete(Category category);
}
