package com.inversionesaraujo.api.business.spec;

import java.time.Month;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.OrderVariety;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.Variety;
import com.inversionesaraujo.api.model.VitroOrder;

import jakarta.persistence.criteria.Join;

public class VitroOrderSpecifications {
    public static Specification<VitroOrder> findByTuberId(Long tuberId) {
        return (root, query, criteriaBuilder) -> {
            if(tuberId == null) return null;

            Join<VitroOrder, OrderVariety> orderVariety = root.join("items");
            Join<OrderVariety, Variety> variety = orderVariety.join("variety");
    
            return criteriaBuilder.equal(variety.get("tuber").get("id"), tuberId);
        };
    }   
    
    public static Specification<VitroOrder> findByStatus(Status status) {
        return (root, query, criteriaBuilder) -> 
            status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
    }

    public static Specification<VitroOrder> findByMonth(Month month) {
        return (root, query, criteriaBuilder) -> month != null 
            ? criteriaBuilder.equal(
                criteriaBuilder.function(
                    "MONTH", 
                    Integer.class, 
                    root.get("finishDate")), month.getValue())
            : null;
    }

    public static Specification<VitroOrder> findByShipType(ShippingType shipType) {
        return (root, query, criteriaBuilder) ->
            shipType != null ? criteriaBuilder.equal(root.get("shippingType"), shipType) : null;
    }
}
