package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.VitroOrder;

public interface IVitroOrder {
    List<VitroOrder> listAll();

    VitroOrder save(VitroOrder vitroOrder);

    VitroOrder findById(Integer id);

    void delete(VitroOrder vitroOrder);

    boolean ifExists(Integer id);
}
