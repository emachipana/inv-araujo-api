package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.VitroOrderDao;
import com.inversionesaraujo.api.model.entity.VitroOrder;
import com.inversionesaraujo.api.service.IVitroOrder;

@Service
public class VitroOrderImpl implements IVitroOrder {
    @Autowired
    private VitroOrderDao orderDao;

    @Transactional(readOnly = true)
    @Override
    public List<VitroOrder> listAll() {
        return orderDao.findAll();
    }

    @Transactional
    @Override
    public VitroOrder save(VitroOrder vitroOrder) {
        return orderDao.save(vitroOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public VitroOrder findById(Integer id) {
        return orderDao.findById(id).orElseThrow(() -> new DataAccessException("El pedido invitro no existe") {});
    }

    @Transactional
    @Override
    public void delete(VitroOrder vitroOrder) {
        orderDao.delete(vitroOrder);
    }

    @Override
    public List<VitroOrder> findByTuberId(Integer tuberId) {
        return orderDao.findByTuberId(tuberId);
    }
}
