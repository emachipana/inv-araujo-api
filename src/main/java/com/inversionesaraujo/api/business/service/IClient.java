package com.inversionesaraujo.api.business.service;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.SortDirection;

public interface IClient {
    Page<Client> filterClients(Integer page, Integer size, SortDirection direction);

    Page<Client> search(String document, String rsocial, String city, String department, Integer page);

    Client save(Client client);

    Client findById(Integer id);

    void delete(Client client);
}
