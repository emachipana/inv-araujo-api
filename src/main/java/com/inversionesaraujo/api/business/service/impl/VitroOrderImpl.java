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
import com.inversionesaraujo.api.business.payload.TotalDeliverResponse;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.business.spec.VitroOrderSpecifications;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.VitroOrder;
import com.inversionesaraujo.api.repository.VitroOrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class VitroOrderImpl implements IVitroOrder {
    @Autowired
    private VitroOrderRepository orderRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Page<VitroOrderDTO> listAll(
        Long tuberId, Integer page, Integer size, 
        SortDirection direction, Month month, Status status,
        SortBy sortby, ShippingType shipType, Boolean ordersReady,
        Long employeeId
    ) {
        Specification<VitroOrder> spec = Specification.where(
            VitroOrderSpecifications.findByTuberId(tuberId)
            .and(VitroOrderSpecifications.findByMonth(month))
            .and(VitroOrderSpecifications.findByStatus(status))
            .and(VitroOrderSpecifications.findByShipType(shipType))
            .and(VitroOrderSpecifications.ordersReady(ordersReady))
            .and(VitroOrderSpecifications.findByEmployee(employeeId))
        );
        Pageable pageable;
        if(sortby != null) {
            Sort sorted = Sort.by(Sort.Direction.fromString(direction.toString()), sortby.toString());
            pageable = PageRequest.of(page, size, sorted);
        }else {
            pageable = PageRequest.of(page, size);
        }

        Page<VitroOrder> orders = orderRepo.findAll(spec, pageable);

        return VitroOrderDTO.toPageableDTO(orders);
    }

    @Transactional
    @Override
    public VitroOrderDTO save(VitroOrderDTO vitroOrder) {
        VitroOrder vitroOrderSaved = orderRepo.save(VitroOrderDTO.toEntity(vitroOrder, entityManager)); 

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

    @Transactional(readOnly = true)
    @Override
    public TotalDeliverResponse totalDeliver() {
        Long total = orderRepo.totalDeliver();

        return TotalDeliverResponse
            .builder()
            .total(total)
            .build();
    }
}
