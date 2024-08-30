package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.LandingOffer;

public interface ILandingOffer {
    List <LandingOffer> listAll();

    LandingOffer save(LandingOffer offer);

    LandingOffer findById(Integer id);

    void delete(LandingOffer offer);

    boolean ifExists(Integer id);
}
