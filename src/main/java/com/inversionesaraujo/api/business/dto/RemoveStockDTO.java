package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Employee;
import com.inversionesaraujo.api.model.Product;
import com.inversionesaraujo.api.model.RemoveStock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveStockDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String reason;
    private EmployeeDTO employee;
    private LocalDateTime createdAt;

    public static RemoveStockDTO toDTO(RemoveStock removeStock) {
        if(removeStock == null) return null;

        return RemoveStockDTO
            .builder()
            .id(removeStock.getId())
            .productId(removeStock.getProduct().getId())
            .quantity(removeStock.getQuantity())
            .reason(removeStock.getReason())
            .employee(EmployeeDTO.toDTO(removeStock.getEmployee()))
            .createdAt(removeStock.getCreatedAt())
            .build();
    }

    public static RemoveStock toEntity(RemoveStockDTO removeStockDTO) {
        if(removeStockDTO == null) return null;

        Product product = new Product();
        product.setId(removeStockDTO.getProductId());

        Employee employee = new Employee();
        employee.setId(removeStockDTO.getEmployee().getId());

        return RemoveStock
            .builder()
            .id(removeStockDTO.getId())
            .product(product)
            .quantity(removeStockDTO.getQuantity())
            .reason(removeStockDTO.getReason())
            .employee(employee)
            .createdAt(removeStockDTO.getCreatedAt())
            .build();
    }

    public static List<RemoveStockDTO> toDTOList(List<RemoveStock> removeStocks) {
        return removeStocks
            .stream()
            .map(RemoveStockDTO::toDTO)
            .collect(Collectors.toList());
    }
}
