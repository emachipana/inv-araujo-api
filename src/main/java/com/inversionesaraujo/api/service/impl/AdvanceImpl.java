package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.AdvanceDao;
import com.inversionesaraujo.api.model.entity.Advance;
import com.inversionesaraujo.api.service.IAdvance;

@Service
public class AdvanceImpl implements IAdvance {
  @Autowired
  private AdvanceDao advanceDao;

  @Transactional
  @Override
  public Advance save(Advance advance) {
    return advanceDao.save(advance);
  }

  @Transactional(readOnly = true)
  @Override
  public Advance findById(Integer id) {
    return advanceDao.findById(id).orElseThrow(() -> new DataAccessException("El adelanto no existe") {});
  }

  @Override
  public void delete(Advance advance) {
    advanceDao.delete(advance);
  }
}
