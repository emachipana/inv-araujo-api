package com.inversionesaraujo.api.service.impl;

import java.time.Month;
import java.util.ArrayList;
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

import com.inversionesaraujo.api.helpers.OrderData;
import com.inversionesaraujo.api.model.dao.OrderDao;
import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.model.entity.Status;
import com.inversionesaraujo.api.model.payload.OrderDataResponse;
import com.inversionesaraujo.api.model.spec.OrderSpecifications;
import com.inversionesaraujo.api.service.IOrder;

@Service
public class OrderImpl implements IOrder {
    @Autowired
    private OrderDao orderDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Order> listAll(Status status, Integer page, Integer size, SortDirection direction) {
        Specification<Order> spec = Specification.where(OrderSpecifications.findByStatus(status));
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "date");
        Pageable pageable = PageRequest.of(page, size, sort);

        return orderDao.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public Order save(Order order) {
        return orderDao.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Order findById(Integer id) {
        return orderDao.findById(id).orElseThrow(() -> new DataAccessException("El pedido no existe") {});
    }

    @Transactional
    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> search(String department, String city, String rsocial) {
        return orderDao.findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(department, city, rsocial);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDataResponse getData() {
        List<Order> orders = orderDao.findAll();
        
        return OrderData.filterData(orders, null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> pending(Month month) {
        List<Order> orders = orderDao.findAll();
        List<Order> result = new ArrayList<>();

        for(int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Status orderStatus = order.getStatus();
            Month orderMonth = order.getMaxShipDate().getMonth();

            if(orderStatus == Status.PENDIENTE && orderMonth == month) result.add(order);
        }

        return result;
    }
}
