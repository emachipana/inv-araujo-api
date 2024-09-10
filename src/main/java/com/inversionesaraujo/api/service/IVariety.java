package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.Variety;

public interface IVariety {
    Variety save(Variety variety);

    Variety findById(Integer id);

    void delete(Variety variety);
}
