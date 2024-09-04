package com.inversionesaraujo.api.service.impl;

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
import com.inversionesaraujo.api.model.dao.ImageDao;
import com.inversionesaraujo.api.model.entity.Image;
import com.inversionesaraujo.api.model.payload.FileResponse;
import com.inversionesaraujo.api.service.I_Image;

@Service
public class ImageImpl implements I_Image {
    @Autowired
    private ImageDao imageRepo;

    @Transactional
    @Override
    public Image save(Image image) {
        return imageRepo.save(image);
    }

    @Transactional(readOnly = true)
    @Override
    public Image findById(Integer id) {
        return imageRepo.findById(id).orElseThrow(() -> new DataAccessException("La imagen no existe") {});
    }

    @Transactional
    @Override
    public void delete(Image image) {
        imageRepo.delete(image);
    }

    @Override
    public FileResponse upload(MultipartFile image) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String name = UUID.randomUUID().toString();
        bucket.create(name, image.getBytes(), image.getContentType());
        String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), name);

        return FileResponse
            .builder()
            .fileUrl(imageUrl)
            .fileName(name)
            .build();
    }

    @Override
    public void deleteImage(String fileName) throws IOException {
        Blob blob = StorageClient.getInstance().bucket().get(fileName);
        blob.delete();
    }
}
