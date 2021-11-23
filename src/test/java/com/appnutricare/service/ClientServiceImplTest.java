package com.appnutricare.service;

import com.appnutricare.entities.Client;
import com.appnutricare.repository.IClientRepository;
import com.appnutricare.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {
    @Mock
    private IClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;
    @Test
    public void saveTest()
    {
        Client client = new Client(1,"JosueCuentas","123","Josue"
                ,"Cuentas","josue.c1012gmail.com",ParseDate("2021-05-05"));
        given(clientRepository.save(client)).willReturn(client);

        Client savedClient = null;
        try{
            savedClient = clientService.save(client);
        }catch (Exception e)
        {

        }

        assertThat(savedClient).isNotNull();
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void findByIdTest() throws Exception
    {
        Integer clientId = 1;
        Client client = new Client(1,"JosueCuentas","123","Josue"
                ,"Cuentas","josue.c1012gmail.com",ParseDate("2021-05-05"));
        given(clientService.getById(clientId)).willReturn(Optional.of(client));

        Optional<Client> expected = null;
        expected = clientService.getById(clientId);
        assertThat(expected).isNotNull();
    }

    @Test
    void findByUsernameTest() throws Exception
    {
        String username = "JosueCuentas";
        Client client = new Client(1,"JosueCuentas","123","Josue"
                ,"Cuentas","josue.c1012gmail.com",ParseDate("2021-05-05"));
        given(clientService.findByUsername(username)).willReturn(client);

        Client expected = null;
        expected = clientService.findByUsername(username);
        assertThat(expected).isNotNull();
    }

    @Test
    void findAllTest() throws Exception
    {
        List<Client> list = new ArrayList<>();
        list.add(new Client(1,"JosueCuentas","123","Josue"
                ,"Cuentas","josue.c1012gmail.com",ParseDate("2021-05-05")));
        list.add(new Client(2,"JosueCuentas","123","Josue"
                ,"Cuentas","josue.c1012gmail.com",ParseDate("2021-05-05")));
        list.add(new Client(3,"JosueCuentas","123","Josue"
                ,"Cuentas","josue.c1012gmail.com",ParseDate("2021-05-05")));

        given(clientRepository.findAll()).willReturn(list);
        List<Client> expected = clientService.getAll();
        assertEquals(expected, list);
    }

    public static Date ParseDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy,MM,dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }

    @Test
    void findByFirstnameTest() throws Exception {
        String FirstName = "Josue1";
        List<Client> clientList;

        clientList = new ArrayList<>();
        clientList.add(new Client(1,"JosueCuentas1","123","Josue1"
                ,"Cuentas1","josue1.c1012gmail.com",ParseDate("2021-05-05")));
        clientList.add(new Client(2,"JosueCuentas2","123","Josue2"
                ,"Cuentas2","josue2.c1012gmail.com",ParseDate("2021-05-05")));
        clientList.add(new Client(3,"JosueCuentas3","123","Josue3"
                ,"Cuentas3","josue3.c1012gmail.com",ParseDate("2021-05-05")));

        given(clientRepository.findByFirstName(FirstName)).willReturn(clientList);
        List<Client> expected = clientService.findByFirstName(FirstName);
        assertThat(expected).isNotNull();
    }

    @Test
    void findByLastnameTest() throws Exception {
        String lastname = "Cuentas1";
        List<Client> clientList;

        clientList = new ArrayList<>();
        clientList.add(new Client(1,"JosueCuentas1","123","Josue1"
                ,"Cuentas1","josue1.c1012gmail.com",ParseDate("2021-05-05")));
        clientList.add(new Client(2,"JosueCuentas2","123","Josue2"
                ,"Cuentas2","josue2.c1012gmail.com",ParseDate("2021-05-05")));
        clientList.add(new Client(3,"JosueCuentas3","123","Josue3"
                ,"Cuentas3","josue3.c1012gmail.com",ParseDate("2021-05-05")));

        given(clientRepository.findByLastName(lastname)).willReturn(clientList);
        List<Client> expected = clientService.findByLastName(lastname);
        assertThat(expected).isNotNull();
    }

    @Test
    void findByFirstnameAndLastnameTest() throws Exception {
        String firstname = "Josue1";
        String lastname = "Cuentas1";
        List<Client> clientList;

        clientList = new ArrayList<>();
        clientList.add(new Client(1,"JosueCuentas1","123","Josue1"
                ,"Cuentas1","josue1.c1012gmail.com",ParseDate("2021-05-05")));
        clientList.add(new Client(2,"JosueCuentas2","123","Josue2"
                ,"Cuentas2","josue2.c1012gmail.com",ParseDate("2021-05-05")));
        clientList.add(new Client(3,"JosueCuentas3","123","Josue3"
                ,"Cuentas3","josue3.c1012gmail.com",ParseDate("2021-05-05")));

        given(clientRepository.findByFirstNameAndLastName(firstname, lastname)).willReturn(clientList);
        List<Client> expected = clientService.findByFirstNameAndLastName(firstname, lastname);
        assertThat(expected).isNotNull();
    }

    @Test
    void deleteTest() throws Exception {
        Integer id = 1;
        clientService.delete(id);
        verify(clientRepository, times(1)).deleteById(id);
    }
}
