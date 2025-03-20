package com.inversionesaraujo.api.business.dto;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.Role;
import com.inversionesaraujo.api.model.User;

import jakarta.persistence.EntityManager;
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
public class ClientDTO {
    private Long id;
    private String city;
    private String department;
    private String phone;
    private String document;
    private DocumentType documentType;
    private Double consumption;
    private String rsocial;
    private Role createdBy;
    private String email;
    private Long userId;

    public static ClientDTO toDTO(Client client) {
        if(client == null) return null;

        return ClientDTO
            .builder()
            .id(client.getId())
            .city(client.getCity())
            .department(client.getDepartment())
            .phone(client.getPhone())
            .document(client.getDocument())
            .documentType(client.getDocumentType())
            .consumption(client.getConsumption())
            .rsocial(client.getRsocial())
            .createdBy(client.getCreatedBy())
            .email(client.getEmail())
            .userId(client.getUser() != null ? client.getUser().getId() : null)
            .build();
    }

    public static Client toEntity(ClientDTO client, EntityManager entityManager) {
        if(client == null) return null;

        User user = new User();
        if(client.getUserId() != null) user = entityManager.getReference(User.class, client.getUserId());
        else user = null;

        return Client
            .builder()
            .id(client.getId())
            .city(client.getCity())
            .department(client.getDepartment())
            .phone(client.getPhone())
            .document(client.getDocument())
            .documentType(client.getDocumentType())
            .consumption(client.getConsumption())
            .rsocial(client.getRsocial())
            .createdBy(client.getCreatedBy())
            .email(client.getEmail())
            .user(user)
            .build(); 
    }

    public static Page<ClientDTO> toPageableDTO(Page<Client> clients) {
        return clients.map(ClientDTO::toDTO);
    }
}
