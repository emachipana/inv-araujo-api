package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Category;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.service.ICategory;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private ICategory categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Category category = categoryService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La categoria se encontro con exito")
                .data(category)
                .build(), HttpStatus.OK);   
        }catch (Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Category newCategory) {
        try {
            Category savedCategory = categoryService.save(newCategory);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La categoria se creo con exito")
                .data(savedCategory)
                .build(), HttpStatus.CREATED);   
        }catch (Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody Category categoryRequest, @PathVariable Integer id) {
        try {
            Category categoryToUpdate = categoryService.findById(id);
            categoryToUpdate.setName(categoryRequest.getName());
            Category updatedCategory = categoryService.save(categoryToUpdate);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La categoria se actualizo con exito")
                .data(updatedCategory)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            Category category = categoryService.findById(id);
            categoryService.delete(category);
            
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La categoria se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
