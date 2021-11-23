package com.appnutricare.controller;
import com.appnutricare.entities.*;
import com.appnutricare.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers =  RecommendationController.class)
@ActiveProfiles("test")

public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationServiceImpl recommendationService;
    private List<Recommendation> recommendationList;
    private List<Nutritionist> nutritionistList;

    @BeforeEach
    void setUp()
    {
        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        recommendationList = new ArrayList<>();
        recommendationList.add(new Recommendation(1,"pera","descripcionRandom1",ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"),nutritionistList.get(0)));
        recommendationList.add(new Recommendation(2,"pera","descripcionRandom2",ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"),nutritionistList.get(1)));
        recommendationList.add(new Recommendation(3,"pera","descripcionRandom3",ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"),nutritionistList.get(2)));
        recommendationList.add(new Recommendation(4,"pera","descripcionRandom4",ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"),nutritionistList.get(3)));
    }

    @Test
    void findAllRecommendations() throws Exception {
        given(recommendationService.getAll()).willReturn(recommendationList);
        mockMvc.perform(get("/api/recommendation")).andExpect(status().isOk());
    }

    @Test
    void findRecommendationById() throws Exception {
        Integer RecommendationId = 1;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));
        Recommendation recommendation = new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionist);
        given(recommendationService.getById(RecommendationId)).willReturn(Optional.of(recommendation));

        mockMvc.perform(get("/api/recommendation/{id}", recommendation.getId())).andExpect(status().isOk());
    }

    @Test
    void findRecommendationByRecommendationDateBetweenDatesTest() throws Exception{
        String date1_string = "2015-07-21 17:32:28";
        String date2_string = "2022-07-21 17:32:28";
        Date date1 = ParseDate2(date1_string);
        Date date2 = ParseDate2(date2_string);
        given(recommendationService.findBetweenDates(date1, date2)).willReturn(recommendationList);
        mockMvc.perform(get("/api/recommendation/searchRecommendationBetweenDates").param("date1", date1_string).param("date2", date2_string)).andExpect(status().isOk());
    }

    @Test
    void findByName() throws Exception
    {
        String RecommendationName = "pera";
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Recommendation recommendation = new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionist);
        given(recommendationService.findByName(RecommendationName)).willReturn(recommendationList);
        mockMvc.perform(get("/api/recommendation/searchByName/{name}", recommendation.getName())).andExpect(status().isOk());
    }

    @Test
    void findByNutritionist() throws Exception
    {
        Integer NutritionistId = 1;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Recommendation recommendation = new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionist);
        given(recommendationService.findByNutritionist(NutritionistId)).willReturn(recommendationList);
        mockMvc.perform(get("/api/recommendation/searchByNutritionistId/{nutritionist_id}", recommendation.getNutritionist().getId())).andExpect(status().isOk());
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