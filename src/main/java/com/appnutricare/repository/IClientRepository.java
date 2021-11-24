package com.appnutricare.repository;

import com.appnutricare.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClientRepository extends JpaRepository<Client, Integer>
{
    public Client findByUsername (String username);
    public List<Client> findByLastName(String lastname);
    public List<Client> findByFirstNameAndLastName(String firstname, String lastname);
    public List<Client> findByFirstName(String firstName);
}
