package com.inversionesaraujo.api.business.service.impl;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.business.spec.VitroOrderSpecifications;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.VitroOrder;
import com.inversionesaraujo.api.repository.VitroOrderRepository;

@Service
public class VitroOrderImpl implements IVitroOrder {
    @Autowired
    private VitroOrderRepository orderRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<VitroOrderDTO> listAll(
        Integer tuberId, Integer page, Integer size, 
        SortDirection direction, Month month, Status status
    ) {
        Specification<VitroOrder> spec = Specification.where(
            VitroOrderSpecifications.findByTuberId(tuberId)
            .and(VitroOrderSpecifications.findByMonth(month))
            .and(VitroOrderSpecifications.findByStatus(status))
        );
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "initDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<VitroOrder> orders = orderRepo.findAll(spec, pageable);

        return VitroOrderDTO.toPageableDTO(orders);
    }

    @Transactional
    @Override
    public VitroOrderDTO save(VitroOrderDTO vitroOrder) {
        VitroOrder vitroOrderSaved = orderRepo.save(VitroOrderDTO.toEntity(vitroOrder)); 

        return VitroOrderDTO.toDTO(vitroOrderSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public VitroOrderDTO findById(Long id) {
        VitroOrder order = orderRepo.findById(id).orElseThrow(() -> new DataAccessException("El pedido invitro no existe") {});

        return VitroOrderDTO.toDTO(order);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VitroOrderDTO> search(String department, String city, String rsocial, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<VitroOrder> orders = orderRepo.findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
            department, city, rsocial, pageable
        );

        return VitroOrderDTO.toPageableDTO(orders);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDataResponse getData() {
        List<Object[]> counts = orderRepo.countOrdersByStatus();
        Object[] result = counts.get(0); 

        return OrderDataResponse
            .builder()
            .ship(((Number) result[0]).longValue())
            .pen(((Number) result[1]).longValue())
            .build();
    }
}
