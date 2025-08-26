package com.inversionesaraujo.api.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "advances")
public class Advance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advance_seq")
    @SequenceGenerator(name = "advance_seq", sequenceName = "advance_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vitro_order_id", nullable = false)
    private VitroOrder vitroOrder;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private PaymentType paymentType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
