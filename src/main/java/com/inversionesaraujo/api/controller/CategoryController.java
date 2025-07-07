package com.inversionesaraujo.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.CategoryDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.ImageDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.CategoryRequest;
import com.inversionesaraujo.api.business.service.ICategory;
import com.inversionesaraujo.api.business.service.I_Image;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private ICategory categoryService;
    @Autowired
    private I_Image imageService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        CategoryDTO category = categoryService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La categoria se encontro con exito")
            .data(category)
            .build());
    }
    
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid CategoryRequest request) {
        ImageDTO image = request.getImageId() == null ? null : imageService.findById(request.getImageId());

        CategoryDTO savedCategory = categoryService.save(CategoryDTO
            .builder()
            .name(request.getName())
            .description(request.getDescription())
            .image(image)
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();

            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo una categoria")
                .redirectTo("/productos")
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("La categoria se creo con exito")
            .data(savedCategory)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid CategoryRequest request, @PathVariable Long id) {
        CategoryDTO category = categoryService.findById(id);
        ImageDTO image = request.getImageId() == null ? category.getImage() : imageService.findById(request.getImageId());

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImage(image);

        CategoryDTO updatedCategory = categoryService.save(category);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();

            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo una categoria")
                .redirectTo("/productos")
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La categoria se actualizo con exito")
            .data(updatedCategory)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        categoryService.delete(id);
        
        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La categoria se elimino con exito")
            .build());
    }
}
