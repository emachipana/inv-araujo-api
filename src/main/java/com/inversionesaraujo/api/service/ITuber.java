package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Tuber;

public interface ITuber {
    List<Tuber> listAll();

    Tuber save(Tuber tuber);

    Tuber findById(Integer id);

    void delete(Tuber tuber);
}
