package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.TuberDao;
import com.inversionesaraujo.api.model.entity.Tuber;
import com.inversionesaraujo.api.service.ITuber;

@Service
public class TuberImpl implements ITuber {
    @Autowired
    private TuberDao tuberDao;

    @Transactional(readOnly = true)
    @Override
    public List<Tuber> listAll() {
        return tuberDao.findAll();
    }

    @Transactional
    @Override
    public Tuber save(Tuber tuber) {
        return tuberDao.save(tuber);
    }

    @Transactional(readOnly = true)
    @Override
    public Tuber findById(Integer id) {
        return tuberDao.findById(id).orElseThrow(() -> new DataAccessException("El tuberculo no existe") {});
    }

    @Transactional
    @Override
    public void delete(Tuber tuber) {
        tuberDao.delete(tuber);
    }
}
