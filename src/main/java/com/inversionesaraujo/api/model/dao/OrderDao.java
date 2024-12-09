package com.inversionesaraujo.api.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.inversionesaraujo.api.model.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
	List<Order> 
		findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
			String department,
			String city,
			String rsocial
		);
}
