package com.inversionesaraujo.api.business.spec;

import java.time.Month;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.OrderVariety;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.Variety;
import com.inversionesaraujo.api.model.VitroOrder;

import jakarta.persistence.criteria.Join;

public class VitroOrderSpecifications {
    public static Specification<VitroOrder> findByTuberId(Long tuberId) {
        return (root, query, cb) -> {
            if(tuberId == null) return null;

            Join<VitroOrder, OrderVariety> orderVariety = root.join("items");
            Join<OrderVariety, Variety> variety = orderVariety.join("variety");
    
            return cb.equal(variety.get("tuber").get("id"), tuberId);
        };
    }   
    
    public static Specification<VitroOrder> findByStatus(Status status) {
        return (root, query, cb) -> 
            status != null ? cb.equal(root.get("status"), status) : null;
    }

    public static Specification<VitroOrder> findByClient(Long clientId) {
        return (root, query, cb) ->
            clientId != null ? cb.equal(root.get("client").get("id"), clientId) : null;
    }

    public static Specification<VitroOrder> findByLocation(OrderLocation location) {
        return (root, query, cb) -> 
            location != null ? cb.equal(root.get("location"), location) : null;
    }

    public static Specification<VitroOrder> ordersReady(Boolean ordersReady) {
        return (root, query, cb) -> 
            ordersReady ? cb.isTrue(root.get("isReady")) : null;
    }

    public static Specification<VitroOrder> findByMonth(Month month) {
        return (root, query, cb) -> month != null
            ? cb.equal(
                cb.function("date_part", Double.class,
                    cb.literal("month"), root.get("finishDate")
                ),
                (double) month.getValue()
            )
            : null;
    }

    public static Specification<VitroOrder> findByDay(Integer day) {
        return (root, query, cb) -> day != null
            ? cb.equal(
                cb.function("date_part", Double.class,
                    cb.literal("day"), root.get("finishDate")
                ),
                (double) day
            )
            : null;
    }

    public static Specification<VitroOrder> findByShipType(ShippingType shipType) {
        return (root, query, cb) ->
            shipType != null ? cb.equal(root.get("shippingType"), shipType) : null;
    }

    public static Specification<VitroOrder> findByEmployee(Long employee_id) {
        return (root, query, cb) ->
            employee_id != null ? cb.equal(root.get("employee").get("id"), employee_id) : null;
    }

    public static Specification<VitroOrder> filterByPending(Double pending) {
        return (root, query, cb) ->
            pending != null ? cb.lessThanOrEqualTo(root.get("pending"), pending) : null;
    }
}
