package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.VitroOrder;

public interface VitroOrderDao extends JpaRepository<VitroOrder, Integer> {}
