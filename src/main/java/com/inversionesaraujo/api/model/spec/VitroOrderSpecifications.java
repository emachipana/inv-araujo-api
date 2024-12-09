package com.inversionesaraujo.api.model.spec;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.entity.OrderVariety;
import com.inversionesaraujo.api.model.entity.Variety;
import com.inversionesaraujo.api.model.entity.VitroOrder;

import jakarta.persistence.criteria.Join;

public class VitroOrderSpecifications {
    public static Specification<VitroOrder> findByTuberId(Integer tuberId) {
        return (root, query, criteriaBuilder) -> {
            if(tuberId == null) return null;

            Join<VitroOrder, OrderVariety> orderVariety = root.join("items");
            Join<OrderVariety, Variety> variety = orderVariety.join("variety");
    
            return criteriaBuilder.equal(variety.get("tuber").get("id"), tuberId);
        };
    }    
}
