package com.appnutricare.controller;

import com.appnutricare.entities.*;
import com.appnutricare.repository.IDietRecipesRepository;
import com.appnutricare.service.IDietService;
import com.appnutricare.service.IRecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/diets")
@Api(tags = "Diet", value = "Servicio Web RESTful de Diets")
public class DietController {

    @Autowired
    private IDietService dietService;
    @Autowired
    private IDietRecipesRepository dietRecipesRepository;
    @Autowired
    private IRecipeService recipeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar todos los Diets", notes = "Método para encontrar todos los Diets")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Diets encontrados"),
            @ApiResponse(code = 404, message = "Diets no encontrados")
    })
    public ResponseEntity<List<Diet>>findAll(){
        try {
            List<Diet> diets = new ArrayList<>();
            diets = dietService.getAll();
            return new ResponseEntity<List<Diet>>(diets, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<List<Diet>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Diets por Id", notes = "Método para encontrar Diets por Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Diets encontrados"),
            @ApiResponse(code = 404, message = "Diets no encontrados")
    })
    public ResponseEntity<Diet> findById(@PathVariable("id") Integer id){
        try {
            Optional<Diet> diet = dietService.getById(id);
            if(!diet.isPresent())
                return new ResponseEntity<Diet>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Diet>(diet.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Diet>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Diets", notes = "Método para agregar un Diet")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Diet agregado"),
            @ApiResponse(code = 404, message = "Diet no fue agregado")
    })
    public ResponseEntity<Diet> insertDiet(@Valid @RequestBody Diet diet){
        try {
            Diet dietNew = dietService.save(diet);
            return ResponseEntity.status(HttpStatus.CREATED).body(dietNew);
        }catch (Exception e){
            return new ResponseEntity<Diet>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualizacion de un Diet", notes = "Método para actualizar un Diet")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Diet actualizado"),
            @ApiResponse(code = 404, message = "Diet no encontrado")
    })
    public ResponseEntity<Diet> updateDiet(@PathVariable("id") Integer id, @Valid @RequestBody Diet diet){
        try {
            Optional<Diet> dietOptional = dietService.getById(id);
            if(!dietOptional.isPresent())
                return new ResponseEntity<Diet>(HttpStatus.NOT_FOUND);
            diet.setId(id);
            dietService.save(diet);
            return new ResponseEntity<Diet>(diet,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Diet>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Diet", notes = "Método para eliminar un Diet")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Diet eliminado"),
            @ApiResponse(code = 404, message = "Diet no encontrado")
    })
    public ResponseEntity<Diet> deleteDiet(@PathVariable("id") Integer id){
        try {
            Optional<Diet> dietOptional = dietService.getById(id);
            if(!dietOptional.isPresent())
                return new ResponseEntity<Diet>(HttpStatus.NOT_FOUND);
            dietService.delete(id);
            return new ResponseEntity<Diet>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Diet>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{recipe_id}/{diet_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adición de Recipe a Diet", notes = "Método que añade una Recipe favorita a un Diet")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recipe añadida a Diet"),
            @ApiResponse(code = 404, message = "Recipe o Diet no encontrado")
    })
    public ResponseEntity<DietRecipes> addRecipeToDiet(@PathVariable("recipe_id") Integer recipe_id,
                                                                   @PathVariable("diet_id") Integer diet_id){
        try {
            Optional<Recipe> foundRecipe = recipeService.getById(recipe_id);
            Optional<Diet> foundDiet = dietService.getById(diet_id);
            if(!foundRecipe.isPresent())
                return new ResponseEntity<DietRecipes>(HttpStatus.NOT_FOUND);
            if(!foundDiet.isPresent())
                return new ResponseEntity<DietRecipes>(HttpStatus.NOT_FOUND);
            DietRecipesFK newFKS = new DietRecipesFK(diet_id, recipe_id);
            DietRecipes dietRecipes = new DietRecipes(newFKS, foundDiet.get(), foundRecipe.get());

            dietRecipesRepository.save(dietRecipes);
            return ResponseEntity.status(HttpStatus.CREATED).body(dietRecipes);
        }catch (Exception e){
            return new ResponseEntity<DietRecipes>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findDietRecipes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipes de un Diet", notes = "Método para listar Recipes de un Diet")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recipes encontrados"),
            @ApiResponse(code = 404, message = "Recipes no encontrados")
    })
    public ResponseEntity<List<Recipe>> findDietRecipes(@PathVariable("id") Integer id)
    {
        try {
            List<Recipe> recipes = new ArrayList<>();
            recipes = dietRecipesRepository.findByDiet(id);
            if(recipes.isEmpty())
                return new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{recipe_id}/{diet_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Recipe de un Diet", notes = "Método para eliminar un Recipe de un Diet")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recipe eliminado"),
            @ApiResponse(code = 404, message = "Recipe no encontrado")
    })
    public ResponseEntity<Diet> deleteRecipeFromDiet(@PathVariable("recipe_id") Integer recipe_id,
                                                             @PathVariable("diet_id") Integer diet_id)
    {
        try{
            DietRecipesFK newFKS = new DietRecipesFK(diet_id, recipe_id);
            Optional<DietRecipes> dietRecipes = dietRecipesRepository.findById(newFKS);
            if(!dietRecipes.isPresent())
                return new ResponseEntity<Diet>(HttpStatus.NOT_FOUND);
            dietRecipesRepository.delete(dietRecipes.get());
            return new ResponseEntity<Diet>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Diet>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
