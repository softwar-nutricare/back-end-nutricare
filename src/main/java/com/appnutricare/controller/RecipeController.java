package com.appnutricare.controller;


import com.appnutricare.entities.Recipe;
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

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/Recipes")
@Api(tags="Recipe", value = "Servicio Web RESTFul de Recipe")
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Recipe", notes="Método para listar todos los recipes")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrados"),
            @ApiResponse(code=404, message = "recipe no encontrados")
    })
    public ResponseEntity<List<Recipe>> findAllRecipe(){
        try{
            List<Recipe> recipe = recipeService.getAll();
            if(recipe.size()>0)
                return new ResponseEntity<List<Recipe>>(recipe, HttpStatus.OK);
            else
                return new ResponseEntity<List<Recipe>>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipe por Id", notes="Método para listar un recipe por Id")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrado"),
            @ApiResponse(code=404, message = "recipe no encontrado")
    })
    public ResponseEntity<Recipe>findRecipeById(@PathVariable("id") Integer id){
        try{
            Optional<Recipe> recipe= recipeService.getById(id);
            if(!recipe.isPresent())
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Recipe>(recipe.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un Recipe de un Nutritionist", notes ="Método que registra un Recipe" )
    @ApiResponses({
            @ApiResponse(code=201, message = "Recipe creado"),
            @ApiResponse(code=404, message = "Recipe no creado")
    })
    public ResponseEntity<Recipe> insertRecipe(@Valid @RequestBody Recipe recipe){
        try{
            Recipe recipeNew = recipeService.save(recipe);
            return ResponseEntity.status(HttpStatus.CREATED).body(recipeNew);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Actualización de datos de Recipe", notes = "Método que actualizar los datos de Recipe")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Recipe actualizados"),
            @ApiResponse(code=404, message = "REcipe no actualizado")
    })
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") Integer id, @Valid @RequestBody Recipe recipe){
        try{
            Optional<Recipe> recipeOld = recipeService.getById(id);
            if(!recipeOld.isPresent())
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            else{
                recipe.setId(id);
                recipeService.save(recipe);
                return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Eliminación de Recipe por Id", notes = "Método que elimina los datos de un Recipe")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Recipe eliminados"),
            @ApiResponse(code=404, message = "Recipe no eliminados")
    })
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable("id") Integer id){
        try{
            Optional<Recipe> recipeDelete = recipeService.getById(id);
            if(recipeDelete.isPresent()){
                recipeService.delete(id);
                return new ResponseEntity<Recipe>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //desde aqui empezare la implementacion de los demas metodos

    //buscar por recipe por nutricionista.

    @GetMapping(value = "/searchRecipeByNutritionistId/{nutritionist_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar recipe por nutritionist id", notes = "Método para encontrar recipe por nutritionist id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recipe encontrados"),
            @ApiResponse(code = 404, message = "Recipe no encontrados")
    })
    public ResponseEntity<List<Recipe>> findByNutritionist(@PathVariable("nutritionist_id") Integer nutritionist_id)
    {
        try{
            List<Recipe> recipes = recipeService.findAllByNutritionist(nutritionist_id);
            if(recipes.size()>0)
                return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
            return new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //buscar recipe por nombre

    @GetMapping(value = "/searchByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipe por name", notes = "Método para encontrar un Recipe por su respectivo name")
    @ApiResponses({
            @ApiResponse(code=201, message = "Recipe encontrado"),
            @ApiResponse(code=404, message = "Recipe no encontrado")
    })
    public ResponseEntity<Recipe> findByName(@PathVariable("name") String name){
        try{
            Recipe recipe = recipeService.findByName(name);
            if(recipe==null)
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Recipe>(recipe,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //buscar recipe entre dates

    @GetMapping(value = "/searchBetweenDates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipe entre fechas", notes = "Método para listar recipe entre fechas")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrados"),
            @ApiResponse(code=404, message = "recipe no encontrados")
    }) //Al requestparam le puedes decir que sea opcional y no necesita estar en el URL
    public ResponseEntity<List<Recipe>> findRecipeByCreated_atBetweenDates(@RequestParam("date1") String date1_string,
                                                                           @RequestParam("date2") String date2_string){
        try{
            Date checking_date = ParseDate(date1_string);
            Date checkout_date = ParseDate(date2_string);
            List<Recipe> recipes = recipeService.findBetweenDates(checking_date, checkout_date);
            if(recipes!=null && recipes.size()>0)
                return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
            else
                return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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