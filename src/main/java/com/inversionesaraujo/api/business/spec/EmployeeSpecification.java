package com.inversionesaraujo.api.business.spec;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.Employee;

public class EmployeeSpecification {
    public static Specification<Employee> belongsToRole(Long roleId) {
        return (root, query, criteriaBuilder) ->
            roleId != null ? criteriaBuilder.equal(root.get("user").get("role").get("id"), roleId) : null;
    }
}
