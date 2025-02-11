package com.inversionesaraujo.api.business.service.impl;

import java.util.UUID;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.inversionesaraujo.api.business.dto.ImageDTO;
import com.inversionesaraujo.api.business.payload.FileResponse;
import com.inversionesaraujo.api.business.service.I_Image;
import com.inversionesaraujo.api.model.Image;
import com.inversionesaraujo.api.repository.ImageRepository;

@Service
public class ImageImpl implements I_Image {
    @Autowired
    private ImageRepository imageRepo;

    @Transactional
    @Override
    public ImageDTO save(ImageDTO image) {
        Image imageSaved = imageRepo.save(ImageDTO.toEntity(image));

        return ImageDTO.toDTO(imageSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public ImageDTO findById(Long id) {
        Image image = imageRepo.findById(id).orElseThrow(() -> new DataAccessException("La imagen no existe") {});

        return ImageDTO.toDTO(image);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        imageRepo.deleteById(id);
    }

    @Override
    public FileResponse upload(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String name = UUID.randomUUID().toString();
        bucket.create(name, file.getBytes(), file.getContentType());
        String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), name);

        return FileResponse
            .builder()
            .fileUrl(imageUrl)
            .fileName(name)
            .build();
    }

    @Override
    public void deleteImage(String filename) {
        Blob blob = StorageClient.getInstance().bucket().get(filename);
        blob.delete();
    }
}
