package com.inversionesaraujo.api.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.entity.VitroOrder;

public interface VitroOrderDao extends JpaRepository<VitroOrder, Integer> {
    @Query("SELECT v FROM VitroOrder v JOIN v.items i JOIN i.variety var JOIN var.tuber t WHERE t.id = :tuberId")
    List<VitroOrder> findByTuberId(@Param("tuberId") Integer tuberId);

    List<VitroOrder> 
        findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
			String department,
			String city,
			String rsocial
        );
  }
