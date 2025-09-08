package com.inversionesaraujo.api.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "notifications")
public class Notification {
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
		@SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
		private Long id;

		@ManyToOne
		@JoinColumn(name = "user_id", nullable = false)
		private User user;

		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		private NotificationType type;

		@Column(nullable = false)
		private String message;

		@CreatedDate
		@Column(updatable = false)
		private LocalDateTime createdAt;

		@Builder.Default
		@Column(nullable = false)
		private Boolean isRead = false;

		@Column(nullable = false)
		private String redirectTo;
}
