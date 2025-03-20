package com.inversionesaraujo.api.business.spec;

import java.time.Month;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;

public class OrderSpecifications {
    public static Specification<Order> findByStatus(Status status) {
        return (root, query, criteriaBuilder) ->
            status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
    }

    public static Specification<Order> findByShipType(ShippingType shipType) {
        return (root, query, criteriaBuilder) ->
            shipType != null ? criteriaBuilder.equal(root.get("shippingType"), shipType) : null;
    }

    public static Specification<Order> findByWarehouse(Long warehouseId) {
        return (root, query, criteriaBuilder) ->
            warehouseId != null ? criteriaBuilder.equal(root.get("warehouse_id"), warehouseId) : null;
    }

    public static Specification<Order> findByMonth(Month month) {
        return (root, query, criteriaBuilder) -> month != null
            ? criteriaBuilder.equal(
                criteriaBuilder.function(
                    "MONTH", 
                    Integer.class, 
                    root.get("maxShipDate")), month.getValue())
            : null;
    }   
}
