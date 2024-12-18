package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.Reset;

public interface IReset {
    Reset save(Reset reset);

    Reset findById(Integer id);

    void delete(Reset reset);

    String generateCode();

    Boolean validCode(Reset reset, String code) throws Exception;
}
