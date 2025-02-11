package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.model.SortDirection;

public interface IProfit {
    List<ProfitDTO> listAll(SortDirection sort);

    ProfitDTO save(ProfitDTO profit);

    ProfitDTO findById(Long id);

    ProfitDTO findByMonth(String month);

    void delete(Long id);
}
