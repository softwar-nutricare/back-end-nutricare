package com.appnutricare.controller;

import com.appnutricare.entities.*;
import com.appnutricare.service.impl.AppointmentServiceImpl;
import com.appnutricare.service.impl.DietServiceImpl;
import com.appnutricare.service.impl.NutritionistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = AppointmentController.class)
@ActiveProfiles("test")
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentServiceImpl appointmentService;
    @MockBean
    private DietServiceImpl dietService;

    private List<Appointment> appointmentList;
    private List<Client> clientList;
    private List<Nutritionist> nutritionistList;
    private List<Diet> dietList;

    @BeforeEach
    void setUp(){
        appointmentList = new ArrayList<>();
        clientList = new ArrayList<>();
        nutritionistList = new ArrayList<>();
        dietList = new ArrayList<>();

        clientList.add(new Client(1, "pepitoasd1", "pepitoasd123", "Joseasd1", "Perezasd1",
                "pepitoasd1@upc.edu.pe", ParseDate("2017-07-21 17:32:28"))); //.10000
        clientList.add(new Client(2, "pepitoasd1", "pepitoasd123", "Joseasd1", "Perezasd1",
                "pepitoasd1@upc.edu.pe", ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        dietList.add(new Diet(1, "diet1", "description1", ParseDate("2017-07-21 17:32:28")));
        dietList.add(new Diet(2, "diet1", "description1", ParseDate("2017-07-21 17:32:28")));

        appointmentList.add(new Appointment(1, clientList.get(0), nutritionistList.get(0), dietList.get(0), ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"), "notes1"));
        appointmentList.add(new Appointment(2, clientList.get(1), nutritionistList.get(1), dietList.get(1), ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"), "notes2"));
    }

    @Test
    void findAllAppointments() throws Exception{
        given(appointmentService.getAll()).willReturn(appointmentList);
        mockMvc.perform(get("/api/appointment")).andExpect(status().isOk());
    }

    @Test
    void findAppointmentById() throws Exception{
        Integer AppointmentId = 1;
        Client client = new Client(1, "pepito1", "pepito123", "Jose1", "Perez1",
                "pepito1@upc.edu.pe", ParseDate("2017-07-21 17:32:28")); //.10000
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Diet diet = new Diet(1, "diet1", "description1", ParseDate("2017-07-21 17:32:28"));

        Appointment appointment = new Appointment(1, client, nutritionist, diet, ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"), "notes1");
        given(appointmentService.getById(AppointmentId)).willReturn(Optional.of(appointment));
        mockMvc.perform(get("/api/appointment/{id}", appointment.getId())).andExpect(status().isOk());
    }

    @Test
    void findAppointmentByAppointmentDateBetweenDates() throws Exception{
        String date1_string = "2015-07-21 17:32:28";
        String date2_string = "2022-07-21 17:32:28";
        Date date1 = ParseDate2(date1_string);
        Date date2 = ParseDate2(date2_string);
        given(appointmentService.findBetweenDates(date1, date2)).willReturn(appointmentList);
        mockMvc.perform(get("/api/appointment/searchBetweenDates").param("date1", date1_string).param("date2", date2_string)).andExpect(status().isOk());
    }

    @Test
    void findAppointmentByNutritionist() throws Exception{
        Integer NutritionistId = 1;
        Client client = new Client(1, "pepito1", "pepito123", "Jose1", "Perez1",
                "pepito1@upc.edu.pe", ParseDate("2017-07-21 17:32:28")); //.10000
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Diet diet = new Diet(1, "diet1", "description1", ParseDate("2017-07-21 17:32:28"));

        Appointment appointment = new Appointment(1, client, nutritionist, diet, ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"), "notes1");
        given(appointmentService.findByNutritionist(NutritionistId)).willReturn(appointmentList);
        mockMvc.perform(get("/api/appointment/searchAppointmentByNutritionistId/{nutritionist_id}", appointment.getNutritionist().getId())).andExpect(status().isOk());
    }

    @Test
    void findAppointmentByClient() throws Exception{
        Integer ClientId = 1;
        Client client = new Client(1, "pepito1", "pepito123", "Jose1", "Perez1",
                "pepito1@upc.edu.pe", ParseDate("2017-07-21 17:32:28")); //.10000
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Diet diet = new Diet(1, "diet1", "description1", ParseDate("2017-07-21 17:32:28"));

        Appointment appointment = new Appointment(1, client, nutritionist, diet, ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"), "notes1");
        given(appointmentService.findByClient(ClientId)).willReturn(appointmentList);
        mockMvc.perform(get("/api/appointment/searchAppointmentByClientId/{client_id}", appointment.getClient().getId())).andExpect(status().isOk());
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

    public static Date ParseDate2(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }

}

