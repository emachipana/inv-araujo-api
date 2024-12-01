package com.inversionesaraujo.api.model.spec;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.entity.Product;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterThanOrEqual(Double minPrice) {
        return (root, query, criteriaBuilder) ->
            minPrice != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice) : null;
    }

    public static Specification<Product> priceLessThanOrEqual(Double maxPrice) {
        return (root, query, criteriaBuilder) ->
            maxPrice != null ? criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice) : null;
    }

    public static Specification<Product> belongsToCategory(Integer categoryId) {
        return (root, query, criteriaBuilder) ->
            categoryId != null ? criteriaBuilder.equal(root.get("category").get("id"), categoryId) : null;
    }
}
