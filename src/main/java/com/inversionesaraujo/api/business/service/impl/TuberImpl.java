package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.ITuber;
import com.inversionesaraujo.api.model.Tuber;
import com.inversionesaraujo.api.repository.TuberRepository;

@Service
public class TuberImpl implements ITuber {
    @Autowired
    private TuberRepository tuberRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Tuber> listAll() {
        return tuberRepo.findAll();
    }

    @Transactional
    @Override
    public Tuber save(Tuber tuber) {
        return tuberRepo.save(tuber);
    }

    @Transactional(readOnly = true)
    @Override
    public Tuber findById(Integer id) {
        return tuberRepo.findById(id).orElseThrow(() -> new DataAccessException("El tuberculo no existe") {});
    }

    @Transactional
    @Override
    public void delete(Tuber tuber) {
        tuberRepo.delete(tuber);
    }
}
