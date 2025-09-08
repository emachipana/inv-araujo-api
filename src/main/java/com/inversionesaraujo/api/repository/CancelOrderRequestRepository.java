package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.CancelOrderRequest;

public interface CancelOrderRequestRepository extends JpaRepository<CancelOrderRequest, Long> {
    List<CancelOrderRequest> findByOrderId(Long orderId);
}
