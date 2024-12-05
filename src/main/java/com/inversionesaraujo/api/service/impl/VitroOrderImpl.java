package com.inversionesaraujo.api.service.impl;

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

import com.inversionesaraujo.api.model.dao.VitroOrderDao;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.model.entity.VitroOrder;
import com.inversionesaraujo.api.model.spec.VitroOrderSpecifications;
import com.inversionesaraujo.api.service.IVitroOrder;

@Service
public class VitroOrderImpl implements IVitroOrder {
    @Autowired
    private VitroOrderDao orderDao;

    @Transactional(readOnly = true)
    @Override
    public Page<VitroOrder> listAll(
        Integer tuberId, Integer page, Integer size, SortDirection direction
    ) {
        Specification<VitroOrder> spec = Specification.where(VitroOrderSpecifications.findByTuberId(tuberId));

        Pageable pageable;
        if(direction != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "initDate");
            pageable = PageRequest.of(page, size, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }

        return orderDao.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public VitroOrder save(VitroOrder vitroOrder) {
        return orderDao.save(vitroOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public VitroOrder findById(Integer id) {
        return orderDao.findById(id).orElseThrow(() -> new DataAccessException("El pedido invitro no existe") {});
    }

    @Transactional
    @Override
    public void delete(VitroOrder vitroOrder) {
        orderDao.delete(vitroOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VitroOrder> search(String department, String city, String rsocial) {
        return orderDao.findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(department, city, rsocial);
    }
}
