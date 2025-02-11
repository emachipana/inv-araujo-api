package com.inversionesaraujo.api.business.service;

import java.time.Month;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

public interface IVitroOrder {
    Page<VitroOrderDTO> listAll(
        Integer tuberId, Integer page, Integer size,
        SortDirection direction, Month month, Status status
    );

    OrderDataResponse getData();

    VitroOrderDTO save(VitroOrderDTO vitroOrder);

    VitroOrderDTO findById(Long id);

    void delete(Long id);

    Page<VitroOrderDTO> search(String department, String city, String rsocial, Integer page);
}
