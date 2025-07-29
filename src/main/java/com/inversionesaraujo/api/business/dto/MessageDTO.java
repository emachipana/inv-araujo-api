package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Message;
import com.inversionesaraujo.api.model.Origin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String subject;
    private String content;
    private Origin origin;
    private String email;
    private LocalDateTime createdAt;

    public static MessageDTO toDTO(Message message) {
        return MessageDTO
            .builder()
            .id(message.getId())
            .fullName(message.getFullName())
            .phone(message.getPhone())
            .subject(message.getSubject())
            .content(message.getContent())
            .origin(message.getOrigin())
            .email(message.getEmail())
            .createdAt(message.getCreatedAt())
            .build();
    }

    public static Message toEntity(MessageDTO message) {
        return Message
            .builder()
            .id(message.getId())
            .fullName(message.getFullName())
            .phone(message.getPhone())
            .subject(message.getSubject())
            .content(message.getContent())
            .origin(message.getOrigin())
            .email(message.getEmail())
            .createdAt(message.getCreatedAt())
            .build();
    }

    public static Page<MessageDTO> toPageableDTO(Page<Message> messages) {
        return messages.map(MessageDTO::toDTO);
    }
}
