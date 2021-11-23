package com.appnutricare.controller;

import com.appnutricare.entities.Bill;
import com.appnutricare.entities.Client;
import com.appnutricare.entities.PaymentMethod;
import com.appnutricare.service.impl.PaymentMethodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentMethodController.class)
@ActiveProfiles("test")
public class PaymentMethodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentMethodServiceImpl paymentMethodService;

    private List<PaymentMethod> paymentMethodList;

    @BeforeEach
    void setUp(){
        Client client = new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        paymentMethodList = new ArrayList<>();
        paymentMethodList.add(new PaymentMethod(1, client, "Visa", 123456L, 25, 2022, 175,
                "Pepito", "Capo", "Lima","Somewhere", "Somewhere2",
                "Lima123", "Peru",123456789));
        paymentMethodList.add(new PaymentMethod(2, client, "Visa", 123456L, 25, 2022, 175,
                "Pepito", "Capo", "Lima","Somewhere", "Somewhere2",
                "Lima123", "Peru",123456789));
        paymentMethodList.add(new PaymentMethod(3, client, "Visa", 123456L, 25, 2022, 175,
                "Pepito", "Capo", "Lima","Somewhere", "Somewhere2",
                "Lima123", "Peru",123456789));
    }

    @Test
    void findAllPaymentMethod() throws Exception {
        given(paymentMethodService.getAll()).willReturn(paymentMethodList);
        mockMvc.perform(get("/api/PaymentMethods")).andExpect(status().isOk());
    }

    @Test
    void findPaymentMethodById() throws Exception{
        Integer paymentMethodId = 1;
        Client client = new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        PaymentMethod paymentMethod = new PaymentMethod(1, client, "Visa", 123456L, 25, 2022, 175,
                "Pepito", "Capo", "Lima","Somewhere", "Somewhere2",
                "Lima123", "Peru",123456789);
        given(paymentMethodService.getById(paymentMethodId)).willReturn(Optional.of(paymentMethod));
        mockMvc.perform(get("/api/PaymentMethods/{id}", paymentMethod.getId())).andExpect(status().isOk());
    }

    @Test
    void findPaymentMethodByClient() throws Exception {
        Integer clientId = 1;
        Client client = new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        PaymentMethod paymentMethod = new PaymentMethod(1, client, "Visa", 123456L, 25, 2022, 175,
                "Pepito", "Capo", "Lima","Somewhere", "Somewhere2",
                "Lima123", "Peru",123456789);
        given(paymentMethodService.findAllByClient(clientId)).willReturn(paymentMethodList);
        mockMvc.perform(get("/api/PaymentMethods/searchPaymentMethodByClientId/{client_id}", paymentMethod.getClient().getId())).andExpect(status().isOk());
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
