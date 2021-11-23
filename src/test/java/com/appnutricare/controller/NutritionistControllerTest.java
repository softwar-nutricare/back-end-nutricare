package com.appnutricare.controller;

import com.appnutricare.entities.Nutritionist;
import com.appnutricare.service.impl.NutritionistServiceImpl;
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

@WebMvcTest(controllers = NutritionistController.class)
@ActiveProfiles("test")
public class NutritionistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NutritionistServiceImpl nutritionistService;

    private List<Nutritionist> nutritionistList;

    @BeforeEach
    void setUp(){
        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
    }

    @Test
    void findAllNutritionists() throws Exception{
        given(nutritionistService.getAll()).willReturn(nutritionistList);
        mockMvc.perform(get("/api/nutritionist")).andExpect(status().isOk());
    }

    @Test
    void findNutritionistById() throws Exception{
        Integer NutritionistId = 1;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistService.getById(NutritionistId)).willReturn(Optional.of(nutritionist));
        mockMvc.perform(get("/api/nutritionist/{id}", nutritionist.getId())).andExpect(status().isOk());
    }

    @Test
    void findNutritionistByUserName() throws Exception{
        String NutritionistUsername = "pepito1";
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistService.findByUsername(NutritionistUsername)).willReturn(nutritionist);
        mockMvc.perform(get("/api/nutritionist/searchByUsername/{username}", nutritionist.getUsername())).andExpect(status().isOk());
    }

    @Test
    void findNutritionistByCpnNumber() throws Exception{
        Integer NutritionistCpnNumber = 123456;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistService.findByCnpNumber(NutritionistCpnNumber)).willReturn(nutritionist);
        mockMvc.perform(get("/api/nutritionist/searchByCnpNumber/{cnp_number}", nutritionist.getCnpNumber())).andExpect(status().isOk());
    }

    @Test
    void findNutritionistByFirstName() throws Exception{
        String NutritionistFirstname = "Jose1";
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistService.findByFirstName(NutritionistFirstname)).willReturn(nutritionistList);
        mockMvc.perform(get("/api/nutritionist/searchByFirstname/{firstname}", nutritionist.getFirstName())).andExpect(status().isOk());
    }

    @Test
    void findNutritionistByLastName() throws Exception{
        String NutritionistLastname = "Perez1";
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistService.findByLastName(NutritionistLastname)).willReturn(nutritionistList);
        mockMvc.perform(get("/api/nutritionist/searchByLastname/{lastname}", nutritionist.getLastName())).andExpect(status().isOk());
    }

    @Test
    void findNutritionistByFirstAndLastName() throws Exception{
        String NutritionistFirstname = "Jose1";
        String NutritionistLastname = "Perez1";
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistService.findByFirstNameAndLastName(NutritionistFirstname, NutritionistLastname)).willReturn(nutritionistList);
        mockMvc.perform(get("/api/nutritionist/searchByFirstnameAndLastname/{firstname}/{lastname}", nutritionist.getFirstName(), nutritionist.getLastName())).andExpect(status().isOk());
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
