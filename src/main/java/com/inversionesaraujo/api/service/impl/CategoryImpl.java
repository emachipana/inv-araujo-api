package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.CategoryDao;
import com.inversionesaraujo.api.model.entity.Category;
import com.inversionesaraujo.api.service.ICategory;

@Service
public class CategoryImpl implements ICategory {
    @Autowired
    private CategoryDao categoryDao;

    @Transactional(readOnly = true)
	@Override
	public List<Category> listAll() {
        return categoryDao.findAll();
    }

    @Transactional
	@Override
	public Category save(Category category) {
        return categoryDao.save(category);
    }

    @Transactional(readOnly = true)
	@Override
	public Category findById(Integer id) {
	    return categoryDao.findById(id).orElseThrow(() -> new DataAccessException("La categoria no existe") {});
    }

    @Transactional
	@Override
	public void delete(Category category) {
        categoryDao.delete(category);
    }
}
