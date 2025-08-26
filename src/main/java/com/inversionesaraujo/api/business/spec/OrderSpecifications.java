package com.inversionesaraujo.api.business.spec;

import java.time.Month;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;

public class OrderSpecifications {
    public static Specification<Order> findByStatus(Status status) {
        return (root, query, cb) ->
            status != null ? cb.equal(root.get("status"), status) : null;
    }

    public static Specification<Order> findByLocation(OrderLocation location) {
        return (root, query, cb) ->
            location != null ? cb.equal(root.get("location"), location) : null;
    }

    public static Specification<Order> findByShipType(ShippingType shipType) {
        return (root, query, cb) ->
            shipType != null ? cb.equal(root.get("shippingType"), shipType) : null;
    }

    public static Specification<Order> findByWarehouse(Long warehouseId) {
        return (root, query, cb) ->
            warehouseId != null ? cb.equal(root.get("warehouse").get("id"), warehouseId) : null;
    }

    public static Specification<Order> findByEmployee(Long employeeId) {
        return (root, query, cb) ->
            employeeId != null ? cb.equal(root.get("employee").get("id"), employeeId) : null;
    }

    public static Specification<Order> findByClient(Long clientId) {
        return (root, query, cb) ->
            clientId != null ? cb.equal(root.get("client").get("id"), clientId) : null;
    }

    public static Specification<Order> findByMonth(Month month) {
        return (root, query, cb) -> month != null
            ? cb.equal(
                cb.function("date_part", Double.class,
                    cb.literal("month"), root.get("maxShipDate")
                ),
                (double) month.getValue()
            )
            : null;
    }
    
    
    public static Specification<Order> findByDay(Integer day) {
        return (root, query, cb) -> day != null
            ? cb.equal(
                cb.function("date_part", Double.class,
                    cb.literal("day"), root.get("maxShipDate")
                ),
                (double) day
            )
            : null;
    }
}
