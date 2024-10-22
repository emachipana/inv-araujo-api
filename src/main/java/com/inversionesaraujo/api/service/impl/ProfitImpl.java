package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ProfitDao;
import com.inversionesaraujo.api.model.entity.Profit;
import com.inversionesaraujo.api.service.IProfit;

@Service
public class ProfitImpl implements IProfit {
    @Autowired
    private ProfitDao profitDao;

    @Transactional(readOnly = true)
    @Override
    public List<Profit> listAll() {
        return profitDao.findAll();
    }

    @Transactional
    @Override
    public Profit save(Profit profit) {
        return profitDao.save(profit);
    }

    @Transactional(readOnly = true)
    @Override
    public Profit findById(Integer id) {
        return profitDao.findById(id).orElseThrow(() -> new DataAccessException("El ingreso no existe") {});
    }

    @Transactional
    @Override
    public void delete(Profit profit) {
        profitDao.delete(profit);
    }

    @Transactional(readOnly = true)
    @Override
    public Profit findByMonth(String month) {
        return profitDao.findByMonth(month);
    }
}
