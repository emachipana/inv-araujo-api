package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.Advance;

public interface IAdvance {
  Advance save(Advance advance);

  Advance findById(Integer id);

  void delete(Advance advance);
}
