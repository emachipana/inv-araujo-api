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
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
		Page<Order> 
			findByClient_DocumentContainingIgnoreCaseOrClient_RsocialContainingIgnoreCaseOrStatusOrShippingType(
				String document,
				String rsocial,
				Status status,
				ShippingType shipType,
				Pageable pageable
			);

		@Query("SELECT o FROM Order o WHERE " +
    "((:search IS NULL) OR (LOWER(o.client.document) LIKE LOWER(concat('%', :search, '%'))) OR " +
    "(LOWER(o.client.rsocial) LIKE LOWER(concat('%', :search, '%')))) AND " +
    "(:status IS NULL OR o.status = :status) AND " +
    "(:shipType IS NULL OR o.shippingType = :shipType)")
		Page<Order> searchOrders(
				@Param("search") String search,
				@Param("status") Status status,
				@Param("shipType") ShippingType shipType,
				Pageable pageable
		);

		@Query("""
				SELECT 
						COALESCE(SUM(CASE WHEN o.status IN ('ENTREGADO', 'ENVIADO') THEN 1 ELSE 0 END), 0), 
						COALESCE(SUM(CASE WHEN o.status IN ('PENDIENTE', 'PAGADO') THEN 1 ELSE 0 END), 0) 
				FROM Order o
		""")
		List<Object[]> countOrdersByGroupedStatus();

		@Query("SELECT o.pickupInfo.hour FROM Order o WHERE o.pickupInfo.date = :date")
		List<LocalTime> findOccupiedPickupHours(@Param("date") LocalDate date);

		@Query("""
			SELECT 
				COALESCE(SUM(CASE WHEN o.shippingType = 'ENVIO_AGENCIA' AND o.status = 'PAGADO' THEN 1 ELSE 0 END), 0),
				COALESCE(SUM(CASE WHEN o.shippingType = 'RECOJO_ALMACEN' AND o.status = 'PAGADO' THEN 1 ELSE 0 END), 0)
			FROM Order o
		""")
		List<Object[]> countPaidOrdersByShippingType();

		Long countByClientId(Long clientId);
}
