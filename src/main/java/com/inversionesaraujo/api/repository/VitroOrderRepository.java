package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.inversionesaraujo.api.model.VitroOrder;

public interface VitroOrderRepository extends JpaRepository<VitroOrder, Integer>, JpaSpecificationExecutor<VitroOrder> {
    List<VitroOrder> 
        findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
			String department,
			String city,
			String rsocial
        );
  }
