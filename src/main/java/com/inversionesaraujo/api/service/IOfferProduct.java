package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.OfferProduct;

public interface IOfferProduct {
    OfferProduct save(OfferProduct item);

    OfferProduct findById(Integer id);

    void delete(OfferProduct item);
}
