package com.inversionesaraujo.api.business.spec;

import java.time.Month;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.Status;

public class OrderSpecifications {
    public static Specification<Order> findByStatus(Status status) {
        return (root, query, criteriaBuilder) ->
            status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
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
