package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ImageDao;
import com.inversionesaraujo.api.model.entity.Image;
import com.inversionesaraujo.api.service.I_Image;

@Service
public class ImageImpl implements I_Image {
    @Autowired
    private ImageDao imageRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Image> listAll() {
        return imageRepo.findAll();
    }

    @Transactional
    @Override
    public Image save(Image image) {
        return imageRepo.save(image);
    }

    @Transactional(readOnly = true)
    @Override
    public Image findById(Integer id) {
        return imageRepo.findById(id).orElseThrow(() -> new DataAccessException("El usuario no existe") {});
    }

    @Transactional
    @Override
    public void delete(Image image) {
        imageRepo.delete(image);
    }
}