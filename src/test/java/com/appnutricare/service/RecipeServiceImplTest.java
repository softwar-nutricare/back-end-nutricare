package com.appnutricare.service;

import com.appnutricare.entities.Nutritionist;
import com.appnutricare.entities.ProfessionalProfile;
import com.appnutricare.entities.Recipe;
import com.appnutricare.repository.IRecipeRepository;
import com.appnutricare.service.impl.RecipeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {
    @Mock
    private IRecipeRepository recipeRepository;
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    public void saveTest(){
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Recipe recipe = new Recipe(1,"Receta numero 1","La receta numero uno esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"),nutritionist);

        given(recipeRepository.save(recipe)).willReturn(recipe);

        Recipe saveRecipe = null;

        try{
            saveRecipe = recipeService.save(recipe);
        }catch (Exception e){
        }

        assertThat(saveRecipe).isNotNull();
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void findByIdTest() throws Exception {
        Integer id = 1;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));

        Recipe recipe = new Recipe(1,"Receta numero 1","La receta numero uno esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"),nutritionist);

        given(recipeRepository.findRecipeById(id)).willReturn(Optional.of(recipe));

        Optional<Recipe> expected = recipeService.getById(id);

        assertThat(expected).isNotNull();
    }

    @Test
    void findAllTest() throws Exception {

        List<Recipe> recipeList;

        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));

        recipeList = new ArrayList<>();
        recipeList.add(new Recipe(1,"Receta numero 1","La receta numero uno esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"),nutritionist));
        recipeList.add(new Recipe(2,"Receta numero 2","La receta numero dos esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"),nutritionist));

        given(recipeRepository.findAll()).willReturn(recipeList);
        List<Recipe> expected = recipeService.getAll();
        assertEquals(expected, recipeList);
    }

    @Test
    void findAllByNutritionist() throws Exception {
        Integer nutritionistId = 1;
        List<Recipe> recipeList;

        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));

        recipeList = new ArrayList<>();
        recipeList.add(new Recipe(1,"Receta numero 1","La receta numero uno esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"),nutritionist));
        recipeList.add(new Recipe(2,"Receta numero 2","La receta numero dos esta conpuesta de frutos etc",
                "Paso1: asdasd, Paso2_asdasdas","Pera,mango,uva",123,ParseDate("2017-07-21 17:32:28"),
                ParseDate("2017-07-21 17:32:28"),nutritionist));

        given(recipeRepository.findAllByNutritionist(nutritionistId)).willReturn(recipeList);
        List<Recipe> expected = recipeService.findAllByNutritionist(nutritionistId);
        assertThat(expected).isNotNull();
    }

    @Test
    void deleteTest() throws Exception {
        Integer id = 1;
        recipeService.delete(1);
        verify(recipeRepository, times(1)).deleteById(id);
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
