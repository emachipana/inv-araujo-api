package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.CategoryDao;
import com.inversionesaraujo.api.model.entity.Category;
import com.inversionesaraujo.api.service.ICategory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements ICategory {
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

    @Transactional(readOnly = true)
	@Override
	public boolean ifExists(Integer id) {
        return categoryDao.existsById(id);
    }
}
