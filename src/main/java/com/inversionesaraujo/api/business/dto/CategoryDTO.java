package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private ImageDTO image;
    private ImageDTO icon;
    private List<CategoryDTO> subCategories;

    public static CategoryDTO toDTO(Category category) {
        if(category == null) return null;
        
        return CategoryDTO
            .builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .categoryId(category.getCategory() != null ? category.getCategory().getId() : null)
            .image(ImageDTO.toDTO(category.getImage()))
            .icon(ImageDTO.toDTO(category.getIcon()))
            .subCategories(toDTOList(category.getSubCategories()))
            .build();
    }

    public static Category toEntity(CategoryDTO category) {
        if(category == null) return null;

        Category parentCategory = new Category();
        if(category.getCategoryId() != null) parentCategory.setId(category.getCategoryId()); 
        else parentCategory = null;

        return Category
            .builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .category(parentCategory)
            .image(ImageDTO.toEntity(category.getImage()))
            .icon(ImageDTO.toEntity(category.getIcon()))
            .build();
    }

    public static List<CategoryDTO> toDTOList(List<Category> categories) {
        if(categories == null) return null;

        return categories
            .stream()
            .map(CategoryDTO::toDTO)
            .collect(Collectors.toList());
    }
}
