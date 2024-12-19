package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.ClientRepository;

@Service
public class ClientImpl implements IClient {
    @Autowired
    private ClientRepository clientRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<Client> filterClients(Integer page, Integer size, SortDirection direction) {
        Pageable pageable;
        if(direction != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "consumption");
            pageable = PageRequest.of(page, size, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }

        return clientRepo.findAll(pageable);
    }

    @Transactional
    @Override
    public Client save(Client client) {
        return clientRepo.save(client);
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Integer id) {
        return clientRepo.findById(id).orElseThrow(() -> new DataAccessException("El client no existe") {});
    }

    @Transactional
    @Override
    public void delete(Client client) {
        clientRepo.delete(client);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Client> search(String document, String rsocial, String city, String department, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        return 
        clientRepo.findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCaseOrCityContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            rsocial, document, city, department, pageable
        );
    }
}
