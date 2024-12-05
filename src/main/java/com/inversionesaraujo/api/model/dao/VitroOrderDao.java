package com.inversionesaraujo.api.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.inversionesaraujo.api.model.entity.VitroOrder;

public interface VitroOrderDao extends JpaRepository<VitroOrder, Integer>, JpaSpecificationExecutor<VitroOrder> {
    List<VitroOrder> 
        findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
			String department,
			String city,
			String rsocial
        );
  }
