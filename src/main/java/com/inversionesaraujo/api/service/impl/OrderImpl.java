package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.OrderDao;
import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.Status;
import com.inversionesaraujo.api.service.IOrder;

@Service
public class OrderImpl implements IOrder {
    @Autowired
    private OrderDao orderDao;

    @Transactional(readOnly = true)
    @Override
    public List<Order> listAll() {
        return orderDao.findAll();
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

    @Override
    public List<Order> findByStatus(Status status) {
        return orderDao.findByStatus(status);
    }
}
