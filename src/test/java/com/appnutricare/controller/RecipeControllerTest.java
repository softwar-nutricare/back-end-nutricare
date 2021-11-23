package com.appnutricare.controller;

import com.appnutricare.entities.Nutritionist;
import com.appnutricare.entities.ProfessionalProfile;
import com.appnutricare.entities.Recipe;
import com.appnutricare.service.impl.RecipeServiceImpl;
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

@WebMvcTest(controllers = RecipeController.class)
@ActiveProfiles("test")
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeServiceImpl recipeService;

    private List<Recipe> recipeList;


    @BeforeEach
    void setUp(){
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21"));
        recipeList = new ArrayList<>();
        recipeList.add(new Recipe(1,"Receta numero 1","La receta numero uno esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21"),
                ParseDate("2017-07-21"),nutritionist));
        recipeList.add(new Recipe(2,"Receta numero 2","La receta numero dos esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21"),
                ParseDate("2017-07-21"),nutritionist));
    }

    @Test
    void findAllRecipe() throws Exception {
        given(recipeService.getAll()).willReturn(recipeList);
        mockMvc.perform(get("/api/Recipes")).andExpect(status().isOk());
    }

    @Test
    void findRecipeById() throws Exception{
        Integer recipeId = 1;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21"));

        Recipe recipe = new Recipe(1,"Receta numero 1","La receta numero uno esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21"),
                ParseDate("2017-07-21"),nutritionist);
      given(recipeService.getById(recipeId)).willReturn(Optional.of(recipe));
        mockMvc.perform(get("/api/Recipes/{id}", recipe.getId())).andExpect(status().isOk());
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
