package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.Variety;

public interface IVariety {
    Variety save(Variety variety);

    Variety findById(Integer id);

    void delete(Variety variety);
}
