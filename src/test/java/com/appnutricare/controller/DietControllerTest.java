package com.appnutricare.controller;



import com.appnutricare.entities.Diet;
import com.appnutricare.repository.IDietRecipesRepository;
import com.appnutricare.service.IRecipeService;
import com.appnutricare.service.impl.DietServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

@WebMvcTest(controllers = DietController.class)
@ActiveProfiles("test")
public class DietControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DietServiceImpl dietService;
    @MockBean
    private IDietRecipesRepository dietRecipesRepository;
    @MockBean
    private IRecipeService recipeService;

    private List<Diet> dietList;

    @BeforeEach
    void setUp(){
        dietList = new ArrayList<>();
        dietList.add(new Diet(1,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));
        dietList.add(new Diet(2,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));
        dietList.add(new Diet(3,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));
        dietList.add(new Diet(4,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));
    }

    @Test
    void findAllDiets() throws Exception{
        given(dietService.getAll()).willReturn(dietList);
        mockMvc.perform(get("/api/diets")).andExpect(status().isOk());
    }

    @Test
    void findDietId() throws Exception{
        Integer DietId = 1;
        Diet diet = new Diet(1,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28"));

        given(dietService.getById(DietId)).willReturn(Optional.of(diet));

        mockMvc.perform(get("/api/diets/{id}", diet.getId())).andExpect(status().isOk());
    }






    public static Date ParseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }
}
