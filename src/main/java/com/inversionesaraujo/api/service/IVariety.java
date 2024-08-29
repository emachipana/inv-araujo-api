package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Variety;

public interface IVariety {
    List<Variety> listAll();

    Variety save(Variety variety);

    Variety findById(Integer id);

    void delete(Variety variety);

    boolean ifExists(Integer id);
}
