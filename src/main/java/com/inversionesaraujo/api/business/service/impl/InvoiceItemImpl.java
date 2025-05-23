package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.InvoiceItemDTO;
import com.inversionesaraujo.api.business.service.I_InvoiceItem;
import com.inversionesaraujo.api.model.InvoiceItem;
import com.inversionesaraujo.api.repository.InvoiceItemRepository;

@Service
public class InvoiceItemImpl implements I_InvoiceItem {
    @Autowired
    private InvoiceItemRepository itemRepo;

    @Transactional
    @Override
    public InvoiceItemDTO save(InvoiceItemDTO item) {
        InvoiceItem itemSaved = itemRepo.save(InvoiceItemDTO.toEntity(item));

        return InvoiceItemDTO.toDTO(itemSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public InvoiceItemDTO findById(Long id) {
        InvoiceItem item = itemRepo.findById(id).orElseThrow(() -> new DataAccessException("El item de la factura no existe") {});

        return InvoiceItemDTO.toDTO(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        itemRepo.deleteById(id);
    }

    @Override
    public List<InvoiceItemDTO> findByInvoiceId(Long invoiceId) {
        List<InvoiceItem> items = itemRepo.findByInvoiceId(invoiceId);

        return InvoiceItemDTO.toListDTO(items);
    }

    @Override
    public Integer countByInvoiceId(Long invoiceId) {
        return itemRepo.countByInvoiceId(invoiceId);
    }
}
