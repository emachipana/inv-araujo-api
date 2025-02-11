package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.CategoryDTO;
import com.inversionesaraujo.api.business.service.ICategory;
import com.inversionesaraujo.api.model.Category;
import com.inversionesaraujo.api.repository.CategoryRepository;

@Service
public class CategoryImpl implements ICategory {
    @Autowired
    private CategoryRepository categoryRepo;

    @Transactional
	@Override
	public CategoryDTO save(CategoryDTO category) {
        Category categortSaved = categoryRepo.save(CategoryDTO.toEntity(category));

        return CategoryDTO.toDTO(categortSaved);
    }

    @Transactional(readOnly = true)
	@Override
	public CategoryDTO findById(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new DataAccessException("La categoria no existe") {});

	    return CategoryDTO.toDTO(category);
    }

    @Transactional
	@Override
	public void delete(Long id) {
        categoryRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> listAll() {
        List<Category> categories = categoryRepo.findByCategoryIsNull();

        return CategoryDTO.toDTOList(categories);
    }
}
