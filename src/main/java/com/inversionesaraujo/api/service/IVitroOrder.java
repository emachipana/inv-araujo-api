package com.inversionesaraujo.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.model.entity.VitroOrder;
import com.inversionesaraujo.api.model.payload.OrderDataResponse;

public interface IVitroOrder {
    Page<VitroOrder> listAll(
        Integer tuberId, Integer page, Integer size,
        SortDirection direction
    );

    OrderDataResponse getData();

    VitroOrder save(VitroOrder vitroOrder);

    VitroOrder findById(Integer id);

    void delete(VitroOrder vitroOrder);

    List<VitroOrder> search(String department, String city, String rsocial);
}
