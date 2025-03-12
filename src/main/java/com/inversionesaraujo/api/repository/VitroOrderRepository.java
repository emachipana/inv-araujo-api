package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.inversionesaraujo.api.model.VitroOrder;

public interface VitroOrderRepository extends JpaRepository<VitroOrder, Long>, JpaSpecificationExecutor<VitroOrder> {
    Page<VitroOrder> 
        findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
			String department,
			String city,
			String rsocial,
            Pageable pageable
        );

    @Query("SELECT " +
       "COALESCE(SUM(CASE WHEN o.status = 'ENTREGADO' THEN 1 ELSE 0 END), 0), " +
       "COALESCE(SUM(CASE WHEN o.status = 'PENDIENTE' THEN 1 ELSE 0 END), 0) " +
       "FROM VitroOrder o")
	List<Object[]> countOrdersByStatus();

    @Query("SELECT COUNT(o) " +
       "FROM VitroOrder o " +
       "WHERE o.status = 'PENDIENTE' " +
       "AND FUNCTION('DATE', o.finishDate) = CURRENT_DATE")
	Long totalDeliver();
  }
