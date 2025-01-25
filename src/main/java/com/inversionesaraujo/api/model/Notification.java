package com.inversionesaraujo.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "notifications")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "El tipo de notificación no puede ir vacío")
	private NotificationType type;
	@Column(nullable = false)
	private String message;
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	@Builder.Default
	private Boolean isRead = false;
	@NotEmpty(message = "El enlace no puede ir vacío")
	private String redirectTo;
}
