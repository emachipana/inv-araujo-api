package com.inversionesaraujo.api.business.spec;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.Notification;

public class NotificationSpecifications {
    public static Specification<Notification> belongsToUser(String username) {
        return (root, query, criteriaBuilder) ->
            username != null ? criteriaBuilder.equal(root.get("user").get("username"), username) : null;        
    }    
}
