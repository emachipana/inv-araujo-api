package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.Advance;

public interface IAdvance {
  Advance save(Advance advance);

  Advance findById(Integer id);

  void delete(Advance advance);
}
