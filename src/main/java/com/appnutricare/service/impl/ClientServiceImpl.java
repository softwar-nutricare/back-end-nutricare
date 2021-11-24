package com.appnutricare.service.impl;

import com.appnutricare.entities.Client;
import com.appnutricare.repository.IClientRepository;
import com.appnutricare.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public List<Client> findByLastName(String lastname) throws Exception {
        return clientRepository.findByLastName(lastname);
    }

    @Override
    public List<Client> findByFirstNameAndLastName(String firstname, String lastname) throws Exception {
        return clientRepository.findByFirstNameAndLastName(firstname, lastname);
    }

    @Override
    public List<Client> findByFirstName(String firstName) throws Exception {
        return clientRepository.findByFirstName(firstName);
    }

    @Override
    public Client findByUsername (String username) throws Exception {
        return clientRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public Client save(Client client) throws Exception {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAll() throws Exception {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getById(Integer id) throws Exception {
        return clientRepository.findById(id);
    }
}
