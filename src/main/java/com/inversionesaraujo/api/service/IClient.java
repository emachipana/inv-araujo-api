package com.inversionesaraujo.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.entity.Client;
import com.inversionesaraujo.api.model.entity.SortDirection;

public interface IClient {
    Page<Client> filterClients(Integer page, Integer size, SortDirection direction);

    List<Client> search(String document, String rsocial, String city, String department);

    Client save(Client client);

    Client findById(Integer id);

    void delete(Client client);
}
