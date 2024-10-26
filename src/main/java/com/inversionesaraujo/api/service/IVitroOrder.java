package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.VitroOrder;

public interface IVitroOrder {
    List<VitroOrder> listAll();

    List<VitroOrder> findByTuberId(Integer tuberId);

    VitroOrder save(VitroOrder vitroOrder);

    VitroOrder findById(Integer id);

    void delete(VitroOrder vitroOrder);

    List<VitroOrder> search(String department, String city, String rsocial);
}
