package com.inversionesaraujo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "El nombre no puede ir vacio")
    @Size(min = 3)
    @Column(unique = true)
    private String name;
    @NotEmpty(message = "La descripción no puede ir vacía")
    @Size(min = 10)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category parenCategory;
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "icon_id")
    private Image icon;
}
