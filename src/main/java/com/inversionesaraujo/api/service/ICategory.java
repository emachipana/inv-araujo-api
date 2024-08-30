package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Category;

public interface ICategory {
    List<Category> listAll();

    Category save(Category category);

    Category findById(Integer id);

    void delete(Category category);

    boolean ifExists(Integer id);
}
