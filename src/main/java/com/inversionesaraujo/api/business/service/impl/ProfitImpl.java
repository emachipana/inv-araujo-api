package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.model.Profit;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.ProfitRepository;

@Service
public class ProfitImpl implements IProfit {
    @Autowired
    private ProfitRepository profitRepo;

    @Transactional(readOnly = true)
    @Override
    public List<ProfitDTO> listAll(SortDirection direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "date");

        List<Profit> profits = profitRepo.findAll(sort);

        return ProfitDTO.toListDTO(profits);
    }

    @Transactional
    @Override
    public ProfitDTO save(ProfitDTO profit) {
        Profit profitSaved = profitRepo.save(ProfitDTO.toEntity(profit));

        return ProfitDTO.toDTO(profitSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public ProfitDTO findById(Long id) {
        Profit profit = profitRepo.findById(id).orElseThrow(() -> new DataAccessException("El ingreso no existe") {});

        return ProfitDTO.toDTO(profit);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        profitRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public ProfitDTO findByMonth(String month) {
        Profit profit = profitRepo.findByMonth(month);
        if(profit == null) return null;

        return ProfitDTO.toDTO(profit);
    }
}
