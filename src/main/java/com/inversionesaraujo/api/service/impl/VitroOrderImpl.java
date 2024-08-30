package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.VitroOrderDao;
import com.inversionesaraujo.api.model.entity.VitroOrder;
import com.inversionesaraujo.api.service.IVitroOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VitroOrderImpl implements IVitroOrder {
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

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return orderDao.existsById(id);
    }
}
