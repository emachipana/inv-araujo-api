package com.inversionesaraujo.api.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.Status;

public interface OrderDao extends JpaRepository<Order, Integer> {
	List<Order> 
		findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
			String department,
			String city,
			String rsocial
		);

	List<Order> findByStatus(Status status);
}
