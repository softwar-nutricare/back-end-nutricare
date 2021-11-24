package com.appnutricare.service;

import com.appnutricare.entities.Client;

import java.util.List;

public interface IClientService extends CrudService<Client> {
    public Client findByUsername (String username) throws Exception;
    public List<Client> findByLastName(String lastname) throws Exception;
    public List<Client> findByFirstNameAndLastName(String firstname, String lastname) throws Exception;
    public List<Client> findByFirstName(String firstName) throws Exception;
}
