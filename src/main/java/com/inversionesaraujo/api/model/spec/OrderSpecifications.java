package com.inversionesaraujo.api.model.spec;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.Status;

public class OrderSpecifications {
    public static Specification<Order> findByStatus(Status status) {
        return (root, query, criteriaBuilder) ->
            status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
    }    
}
