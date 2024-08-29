package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.LandingOfferProduct;

public interface ILandingOfferProduct {
    LandingOfferProduct save(LandingOfferProduct item);

    LandingOfferProduct findById(Integer id);

    void delete(LandingOfferProduct item);

    boolean ifExists(Integer id);
}
