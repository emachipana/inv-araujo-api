package com.inversionesaraujo.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inversionesaraujo.api.model.entity.Image;
import com.inversionesaraujo.api.model.payload.FileResponse;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.service.I_Image;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    @Autowired
    private I_Image imageService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Image image = imageService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La imagen se encontro con exito")
                .data(image)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> save(@RequestParam MultipartFile file) {
        try {
            FileResponse response = imageService.upload(file);
            Image image = imageService.save(Image
                .builder()
                .firebaseId(response.getFileName())
                .url(response.getFileUrl())
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La imagen se creo con exito")
                .data(image)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            Image image = imageService.findById(id);
            imageService.deleteImage(image.getFirebaseId());
            imageService.delete(image);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La imagen se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
