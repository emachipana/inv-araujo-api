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
import com.inversionesaraujo.api.business.service.IOrder;
import com.inversionesaraujo.api.business.spec.OrderSpecifications;
import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.repository.OrderRepository;
import com.inversionesaraujo.api.utils.OrderData;

@Service
public class OrderImpl implements IOrder {
    @Autowired
    private OrderRepository orderRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<Order> listAll(Status status, Integer page, Integer size, SortDirection direction, Month month) {
        Specification<Order> spec = Specification.where(
            OrderSpecifications.findByStatus(status)
            .and(OrderSpecifications.findByMonth(month))
        );
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "date");
        Pageable pageable = PageRequest.of(page, size, sort);

        return orderRepo.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Order findById(Integer id) {
        return orderRepo.findById(id).orElseThrow(() -> new DataAccessException("El pedido no existe") {});
    }

    @Transactional
    @Override
    public void delete(Order order) {
        orderRepo.delete(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Order> search(String department, String city, String rsocial, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        return orderRepo.findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(department, city, rsocial, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDataResponse getData() {
        List<Order> orders = orderRepo.findAll();
        
        return OrderData.filterData(orders, null);
    }
}
