package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.CancelOrderRequestDTO;

public interface ICancelOrderRequest {
    CancelOrderRequestDTO save(CancelOrderRequestDTO cancelOrderRequestDTO);

    boolean acceptRequest(Long id);

    boolean rejectRequest(Long id);

    CancelOrderRequestDTO findById(Long id);

    void delete(Long id);

    List<CancelOrderRequestDTO> findByOrderId(Long orderId);
}
