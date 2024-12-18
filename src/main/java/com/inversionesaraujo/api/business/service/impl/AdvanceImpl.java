package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IAdvance;
import com.inversionesaraujo.api.model.Advance;
import com.inversionesaraujo.api.repository.AdvanceRepository;

@Service
public class AdvanceImpl implements IAdvance {
  @Autowired
  private AdvanceRepository advanceRepo;

  @Transactional
  @Override
  public Advance save(Advance advance) {
    return advanceRepo.save(advance);
  }

  @Transactional(readOnly = true)
  @Override
  public Advance findById(Integer id) {
    return advanceRepo.findById(id).orElseThrow(() -> new DataAccessException("El adelanto no existe") {});
  }

  @Override
  public void delete(Advance advance) {
    advanceRepo.delete(advance);
  }
}
