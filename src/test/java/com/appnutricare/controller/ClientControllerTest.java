package com.appnutricare.controller;

import com.appnutricare.entities.Client;
import com.appnutricare.service.impl.ClientServiceImpl;
import com.appnutricare.service.impl.RecipeServiceImpl;
import com.appnutricare.repository.IClientFavoriteRecipesRepository;
import com.appnutricare.service.IRecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers =  ClientController.class)
@ActiveProfiles("test")
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientServiceImpl clientService;
    @MockBean
    private RecipeServiceImpl recipeService;
    @MockBean
    private IClientFavoriteRecipesRepository clientFavoriteRecipesRepository;


    private List<Client> clientList;

    @BeforeEach
    void setUp()
    {
        clientList = new ArrayList<>();
        clientList.add(new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28")));
        clientList.add(new Client(2,"JosueCuentas2","123","Josue2",
                "Cuentas2","josue2.c1012gmail.com",ParseDate("2017-07-21 17:32:28")));
        clientList.add(new Client(3,"JosueCuentas3","123","Josue3",
                "Cuentas3","josue3.c1012gmail.com",ParseDate("2017-07-21 17:32:28")));
    }

    @Test
    void findAllClients() throws Exception {
        given(clientService.getAll()).willReturn(clientList);
        mockMvc.perform(get("/api/clients")).andExpect(status().isOk());
    }

    @Test
    void findClientById() throws Exception {
        Integer clientId = 1;
        Client client = new Client(1,"JosueCuentas","123","Josue",
                "Cuentas","josue.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        given(clientService.getById(clientId)).willReturn(Optional.of(client));

        mockMvc.perform(get("/api/clients/{id}", client.getId())).andExpect(status().isOk());
    }

    @Test
    void findByLastName() throws Exception {
        String lastname = "Cuentas1";
        Client client = new Client(1,"JosueCuentas1","123","Josue1"
                ,"Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        given(clientService.findByLastName(lastname)).willReturn(clientList);
        mockMvc.perform(get("/api/clients/searchByLastName/{lastname}", client.getLastName())).andExpect(status().isOk());
    }

    @Test
    void findByFirstName() throws Exception
    {
        String firstname = "Josue1";
        Client client = new Client(1,"JosueCuentas1","123","Josue1"
                ,"Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        given(clientService.findByFirstName(firstname)).willReturn(clientList);
        mockMvc.perform(get("/api/clients/searchByFirstName/{firstname}", client.getFirstName())).andExpect(status().isOk());
    }

    @Test
    void findByFirstNameAndLastName() throws Exception
    {
        String firstname = "Josue1";
        String lastname = "Cuentas1";
        Client client = new Client(1,"JosueCuentas1","123","Josue1"
                ,"Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        given(clientService.findByFirstNameAndLastName(firstname, lastname)).willReturn(clientList);
        mockMvc.perform(get("/api/clients/searchByFirstNameAndLastName/{firstname}/{lastname}", client.getFirstName(), client.getLastName())).andExpect(status().isOk());
    }

    @Test
    void findClientByUsername() throws Exception {
        String username = "JosueCuentas1";
        Client client = new Client(1,"JosueCuentas1","123","Josue"
                ,"Cuentas","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        given(clientService.findByUsername(username)).willReturn(client);

        mockMvc.perform(get("/api/clients/searchByUsername/{username}", client.getUsername())).andExpect(status().isOk());
    }

    public static Date ParseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }
}