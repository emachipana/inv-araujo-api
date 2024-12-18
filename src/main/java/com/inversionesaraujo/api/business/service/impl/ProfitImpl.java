package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Profit> listAll(SortDirection direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "date");

        return profitRepo.findAll(sort);
    }

    @Transactional
    @Override
    public Profit save(Profit profit) {
        return profitRepo.save(profit);
    }

    @Transactional(readOnly = true)
    @Override
    public Profit findById(Integer id) {
        return profitRepo.findById(id).orElseThrow(() -> new DataAccessException("El ingreso no existe") {});
    }

    @Transactional
    @Override
    public void delete(Profit profit) {
        profitRepo.delete(profit);
    }

    @Transactional(readOnly = true)
    @Override
    public Profit findByMonth(String month) {
        return profitRepo.findByMonth(month);
    }
}
