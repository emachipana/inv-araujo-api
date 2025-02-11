package com.inversionesaraujo.api.business.service;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.model.SortDirection;

public interface IClient {
    Page<ClientDTO> filterClients(Integer page, Integer size, SortDirection direction);

    Page<ClientDTO> search(String document, String rsocial, String city, String department, Integer page);

    ClientDTO save(ClientDTO client);

    ClientDTO findById(Long id);

    void delete(Long id);
}
