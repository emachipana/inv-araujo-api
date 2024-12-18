package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.model.Tuber;

public interface ITuber {
    List<Tuber> listAll();

    Tuber save(Tuber tuber);

    Tuber findById(Integer id);

    void delete(Tuber tuber);
}
