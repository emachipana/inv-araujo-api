package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.TuberDTO;

public interface ITuber {
    List<TuberDTO> listAll();

    TuberDTO save(TuberDTO tuber);

    TuberDTO findById(Long id);

    void delete(Long id);
}
