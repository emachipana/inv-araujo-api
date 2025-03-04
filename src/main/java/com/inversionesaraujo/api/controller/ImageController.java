package com.inversionesaraujo.api.controller;

import java.io.IOException;

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

import com.inversionesaraujo.api.business.dto.ImageDTO;
import com.inversionesaraujo.api.business.payload.FileResponse;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.service.I_Image;
import com.inversionesaraujo.api.model.ImageType;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    @Autowired
    private I_Image imageService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        ImageDTO image = imageService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La imagen se encontro con exito")
            .data(image)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> save(@RequestParam MultipartFile file) {
        try {
            FileResponse response = imageService.upload(file);
            ImageDTO image = imageService.save(ImageDTO
                .builder()
                .url(response.getFileUrl())
                .type(ImageType.IMAGE)
                .firebaseId(response.getFileName())
                .build());

            return ResponseEntity.status(201).body(MessageResponse
                .builder()
                .message("La imagen se creo con exito")
                .data(image)
                .build());
        }catch(IOException error) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message(error.getMessage())
                .build());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        ImageDTO image = imageService.findById(id);
        imageService.deleteImage(image.getFirebaseId());
        imageService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La imagen se elimino con exito")
            .build());
    }
}
