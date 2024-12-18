package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.OfferProduct;

public interface IOfferProduct {
    OfferProduct save(OfferProduct item);

    OfferProduct findById(Integer id);

    void delete(OfferProduct item);
}
