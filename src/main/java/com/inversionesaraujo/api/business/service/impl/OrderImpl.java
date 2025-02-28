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

import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.service.IOrder;
import com.inversionesaraujo.api.business.spec.OrderSpecifications;
import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.repository.OrderRepository;

@Service
public class OrderImpl implements IOrder {
    @Autowired
    private OrderRepository orderRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> listAll(Status status, Integer page, Integer size, SortDirection direction, Month month) {
        Specification<Order> spec = Specification.where(
            OrderSpecifications.findByStatus(status)
            .and(OrderSpecifications.findByMonth(month))
        );
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "date");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orders = orderRepo.findAll(spec, pageable);

        return OrderDTO.toPageableDTO(orders);
    }

    @Transactional
    @Override
    public OrderDTO save(OrderDTO order) {
        Order orderSaved = orderRepo.save(OrderDTO.toEntity(order));

        return OrderDTO.toDTO(orderSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new DataAccessException("El pedido no existe") {});

        return OrderDTO.toDTO(order);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> search(String department, String city, String rsocial, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Order> orders = orderRepo
            .findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
                department, city, rsocial, pageable
            );

        return OrderDTO.toPageableDTO(orders);
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
