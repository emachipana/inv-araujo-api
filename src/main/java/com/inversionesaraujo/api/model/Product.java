package com.inversionesaraujo.api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "El nombre no puede ir vacío")
    @Size(min = 3, max = 100)
    private String name;
    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "La descripcion no puede ir vacía")
    private String description;
    @NotEmpty(message = "La marca no puede ir vacía")
    private String brand;
    @NotEmpty(message = "La unidad de medida no puede ir vacía")
    private String unit;
    @NotNull(message = "El precio no puede ir vacío")
    @PositiveOrZero
    private Double price;
    @NotNull(message = "El precio de compra no puede ir vacío")
    @PositiveOrZero
    private Double purchasePrice;
    @NotNull(message = "El stock no puede ir vacío")
    @PositiveOrZero
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "El id de la categoria no puede ir vacía")
    private Category category;
    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Discount discount;
}
