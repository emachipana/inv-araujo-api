package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.TuberDTO;
import com.inversionesaraujo.api.business.service.ITuber;
import com.inversionesaraujo.api.model.Tuber;
import com.inversionesaraujo.api.repository.TuberRepository;

@Service
public class TuberImpl implements ITuber {
    @Autowired
    private TuberRepository tuberRepo;

    @Transactional(readOnly = true)
    @Override
    public List<TuberDTO> listAll() {
        List<Tuber> tubers = tuberRepo.findAll();

        return TuberDTO.toListDTO(tubers);
    }

    @Transactional
    @Override
    public TuberDTO save(TuberDTO tuber) {
        Tuber tuberSaved = tuberRepo.save(TuberDTO.toEntity(tuber));

        return TuberDTO.toDTO(tuberSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public TuberDTO findById(Long id) {
        Tuber tuber = tuberRepo.findById(id).orElseThrow(() -> new DataAccessException("El tuberculo no existe") {});

        return TuberDTO.toDTO(tuber);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        tuberRepo.deleteById(id);
    }
}
