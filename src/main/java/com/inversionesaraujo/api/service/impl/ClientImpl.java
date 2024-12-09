package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ClientDao;
import com.inversionesaraujo.api.model.entity.Client;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.service.IClient;

@Service
public class ClientImpl implements IClient {
    @Autowired
    private ClientDao clientDao;

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

        return clientDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Integer id) {
        return clientDao.findById(id).orElseThrow(() -> new DataAccessException("El client no existe") {});
    }

    @Transactional
    @Override
    public void delete(Client client) {
        clientDao.delete(client);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> search(String document, String rsocial, String city, String department) {
        return 
        clientDao.findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCaseOrCityContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            rsocial, document, city, department
        );
    }
}
