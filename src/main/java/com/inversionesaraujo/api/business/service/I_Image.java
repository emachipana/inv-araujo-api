package com.inversionesaraujo.api.business.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.inversionesaraujo.api.business.dto.ImageDTO;
import com.inversionesaraujo.api.business.payload.FileResponse;

public interface I_Image {
    ImageDTO save(ImageDTO image);

    ImageDTO findById(Long id);

    void delete(Long id);

    FileResponse upload(MultipartFile file) throws IOException;

    void deleteImage(String filename);
}
