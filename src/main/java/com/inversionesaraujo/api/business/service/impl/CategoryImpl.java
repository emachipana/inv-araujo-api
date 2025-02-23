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
        Category categorySaved = categoryRepo.save(CategoryDTO.toEntity(category));
        Integer subcategories = 0;
        if(category.getId() != null) subcategories = categoryRepo.countByCategoryId(category.getId());

        return CategoryDTO.toDTO(categorySaved, subcategories);
    }

    @Transactional(readOnly = true)
	@Override
	public CategoryDTO findById(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new DataAccessException("La categoria no existe") {});
        Integer subcategories = categoryRepo.countByCategoryId(category.getId());

	    return CategoryDTO.toDTO(category, subcategories);
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

        return CategoryDTO.toDTOList(categories, categoryRepo);
    }

    @Override
    public List<CategoryDTO> getSubCategories(Long categoryId) {
        List<Category> subcategories = categoryRepo.findByCategoryId(categoryId);

        return CategoryDTO.toDTOList(subcategories, categoryRepo);
    }
}
