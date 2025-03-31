package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.WarehouseProductDTO;

public interface IWarehouseProduct {
    WarehouseProductDTO save(WarehouseProductDTO item);

    WarehouseProductDTO findById(Long id);

    WarehouseProductDTO existingItem(Long productId, Long warehouseId);

    void delete(Long id);
}
