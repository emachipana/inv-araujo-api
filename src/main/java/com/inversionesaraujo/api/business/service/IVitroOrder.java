package com.inversionesaraujo.api.business.service;

import java.time.Month;
import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.payload.OrderDataResponse;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.VitroOrder;

public interface IVitroOrder {
    Page<VitroOrder> listAll(
        Integer tuberId, Integer page, Integer size,
        SortDirection direction, Month month, Status status
    );

    OrderDataResponse getData();

    VitroOrder save(VitroOrder vitroOrder);

    VitroOrder findById(Integer id);

    void delete(VitroOrder vitroOrder);

    List<VitroOrder> search(String department, String city, String rsocial);
}
