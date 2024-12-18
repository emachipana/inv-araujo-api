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

import com.inversionesaraujo.api.business.dto.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.business.spec.VitroOrderSpecifications;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.VitroOrder;
import com.inversionesaraujo.api.repository.VitroOrderRepository;
import com.inversionesaraujo.api.utils.OrderData;

@Service
public class VitroOrderImpl implements IVitroOrder {
    @Autowired
    private VitroOrderRepository orderRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<VitroOrder> listAll(
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

        return orderRepo.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public VitroOrder save(VitroOrder vitroOrder) {
        return orderRepo.save(vitroOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public VitroOrder findById(Integer id) {
        return orderRepo.findById(id).orElseThrow(() -> new DataAccessException("El pedido invitro no existe") {});
    }

    @Transactional
    @Override
    public void delete(VitroOrder vitroOrder) {
        orderRepo.delete(vitroOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VitroOrder> search(String department, String city, String rsocial) {
        return orderRepo.findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(department, city, rsocial);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDataResponse getData() {
        List<VitroOrder> orders = orderRepo.findAll();

        return OrderData.filterData(null, orders);
    }
}
