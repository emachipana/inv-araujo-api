package com.inversionesaraujo.api.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.inversionesaraujo.api.model.entity.Image;
import com.inversionesaraujo.api.model.payload.ImageResponse;

public interface I_Image {
    Image save(Image image);

    Image findById(Integer id);

    void delete(Image image);

    ImageResponse upload(MultipartFile image) throws IOException;

    void deleteImage(String fileName) throws IOException;
}
