package com.inversionesaraujo.api.business.spec;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.Category;
import com.inversionesaraujo.api.model.Product;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterThanOrEqual(Double minPrice) {
        return (root, query, criteriaBuilder) ->
            minPrice != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice) : null;
    }

    public static Specification<Product> priceLessThanOrEqual(Double maxPrice) {
        return (root, query, criteriaBuilder) ->
            maxPrice != null ? criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice) : null;
    }

    public static Specification<Product> stockLessThanOrEqual(Integer stock) {
        return (root, query, criteriaBuilder) ->
            stock != null ? criteriaBuilder.lessThanOrEqualTo(root.get("stock"), stock) : null;
    }

    public static Specification<Product> belongsToCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) return null;

            var subquery = query.subquery(Long.class);
            var subRoot = subquery.from(Category.class);
            subquery.select(subRoot.get("id"))
                    .where(criteriaBuilder.equal(subRoot.get("category").get("id"), categoryId));

            return criteriaBuilder.or(
                criteriaBuilder.equal(root.get("category").get("id"), categoryId),
                root.get("category").get("id").in(subquery)
            );
        };
    }

    public static Specification<Product> findByCategoryName(String name) {
        return (root, query, criteriaBuilder) ->
            name != null ? criteriaBuilder.equal(root.get("category").get("name"), name) : null;
    }

    public static Specification<Product> hasDiscount(Boolean withDiscounts) {
        return (root, query, criteriaBuilder) -> 
            withDiscounts ? criteriaBuilder.isNotNull(root.get("discount")) : null;
    }    
}
