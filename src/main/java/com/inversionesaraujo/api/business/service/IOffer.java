package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.model.Offer;

public interface IOffer {
    List <Offer> listAll();

    Offer save(Offer offer);

    Offer findById(Integer id);

    void delete(Offer offer);
}
