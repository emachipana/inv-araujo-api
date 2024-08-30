package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Image;

public interface I_Image {
    List<Image> listAll();

    Image save(Image image);

    Image findById(Integer id);

    void delete(Image image);

    boolean ifExists(Integer id);
}
