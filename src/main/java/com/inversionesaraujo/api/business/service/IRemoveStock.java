package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.RemoveStockDTO;

public interface IRemoveStock {
    public RemoveStockDTO create(RemoveStockDTO removeStockDTO);

    public List<RemoveStockDTO> findByProductId(Long productId);
}
