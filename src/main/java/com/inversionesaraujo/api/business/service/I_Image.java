package com.inversionesaraujo.api.business.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.inversionesaraujo.api.business.dto.payload.FileResponse;
import com.inversionesaraujo.api.model.Image;

public interface I_Image {
    Image save(Image image);

    Image findById(Integer id);

    void delete(Image image);

    FileResponse upload(MultipartFile file) throws IOException;

    void deleteImage(String fileName) throws IOException;
}
