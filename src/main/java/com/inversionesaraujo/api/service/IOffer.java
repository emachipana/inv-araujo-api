package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Offer;

public interface IOffer {
    List <Offer> listAll();

    Offer save(Offer offer);

    Offer findById(Integer id);

    void delete(Offer offer);
}