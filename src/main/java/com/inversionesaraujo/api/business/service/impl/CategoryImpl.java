package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.ICategory;
import com.inversionesaraujo.api.model.Category;
import com.inversionesaraujo.api.repository.CategoryRepository;

@Service
public class CategoryImpl implements ICategory {
    @Autowired
    private CategoryRepository categoryRepo;

    @Transactional
	@Override
	public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Transactional(readOnly = true)
	@Override
	public Category findById(Integer id) {
	    return categoryRepo.findById(id).orElseThrow(() -> new DataAccessException("La categoria no existe") {});
    }

    @Transactional
	@Override
	public void delete(Category category) {
        categoryRepo.delete(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> listAll() {
        return categoryRepo.findAll();
    }
}
