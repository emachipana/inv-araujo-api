package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.RemoveStockDTO;
import com.inversionesaraujo.api.business.service.IRemoveStock;
import com.inversionesaraujo.api.model.RemoveStock;
import com.inversionesaraujo.api.repository.RemoveStockRepository;

@Service
public class RemoveStockImpl implements IRemoveStock {
    @Autowired
    private RemoveStockRepository repo;
    @Autowired
    private ProductImpl productService;

    @Override
    public RemoveStockDTO create(RemoveStockDTO removeStockDTO) {
        ProductDTO product = productService.findById(removeStockDTO.getProductId());
        RemoveStock removeStockSaved = repo.save(RemoveStockDTO.toEntity(removeStockDTO));

        Integer newStock = product.getStock() - removeStockDTO.getQuantity();
        product.setStock(newStock);
        productService.save(product);

        return RemoveStockDTO.toDTO(removeStockSaved);
    }

    @Override
    public List<RemoveStockDTO> findByProductId(Long productId) {
        List<RemoveStock> remove = repo.findByProductId(productId);

        return RemoveStockDTO.toDTOList(remove);
    }  
}
