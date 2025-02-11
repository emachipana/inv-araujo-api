package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.ResetDTO;

public interface IReset {
    ResetDTO save(ResetDTO reset);

    ResetDTO findById(Long id);

    void delete(Long id);

    String generateCode();

    Boolean validCode(ResetDTO reset, String code);
}
