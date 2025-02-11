package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.AdvanceDTO;

public interface IAdvance {
  AdvanceDTO save(AdvanceDTO advance);

  AdvanceDTO findById(Long id);

  void delete(Long id);
}
