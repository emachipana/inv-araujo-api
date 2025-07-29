package com.inversionesaraujo.api.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
		Page<Order> 
			findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
				String department,
				String city,
				String rsocial,
				Pageable pageable
			);

		@Query("SELECT " +
				"COALESCE(SUM(CASE WHEN o.status = 'ENTREGADO' THEN 1 ELSE 0 END), 0), " +
				"COALESCE(SUM(CASE WHEN o.status = 'PENDIENTE' THEN 1 ELSE 0 END), 0) " +
				"FROM Order o")
		List<Object[]> countOrdersByStatus();

		@Query("SELECT COUNT(o) " +
				"FROM Order o " +
				"WHERE o.status = 'PENDIENTE' " +
				"AND FUNCTION('DATE', o.maxShipDate) = CURRENT_DATE")
		Long totalDeliver();

		@Query("SELECT o.pickupInfo.hour FROM Order o WHERE o.pickupInfo.date = :date")
		List<LocalTime> findOccupiedPickupHours(@Param("date") LocalDate date);
}
