package com.appnutricare.controller;


import com.appnutricare.entities.Nutritionist;
import com.appnutricare.service.INutritionistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/nutritionist")
@Api(tags="Nutritionist", value="Servicio Web RESTFul de Nutritionist")
public class NutritionistController {

    @Autowired
    private INutritionistService nutritionistService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Nutritionists", notes = "Método que registra customers en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nutritionist creado"),
            @ApiResponse(code = 404, message = "Nutritionist no creado")
    })
    public ResponseEntity<Nutritionist> insertNutritionist(@Valid @RequestBody Nutritionist nutritionist) {
        try {
            Nutritionist nutritionistNew = nutritionistService.save(nutritionist);
            return ResponseEntity.status(HttpStatus.CREATED).body(nutritionistNew);
        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Nutritionists", notes = "Método que actualiza los datos de Nutritionists")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Datos de Nutritionist actualizado"),
            @ApiResponse(code = 404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> updateNutritionist(
            @PathVariable("id") Integer id, @Valid @RequestBody Nutritionist nutritionist) {
        try {
            Optional<Nutritionist> nutritionistUp = nutritionistService.getById(id);
            if (!nutritionistUp.isPresent())
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            nutritionist.setId(id);
            nutritionistService.save(nutritionist);
            return new ResponseEntity<Nutritionist>(nutritionist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de datos de Nutritionist", notes = "Método que elimina los datos de Nutritionist en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Datos de Nutritionist eliminados"),
            @ApiResponse(code = 404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> deleteNutritionist(@PathVariable("id") Integer id) {
        try {
            Optional<Nutritionist> nutritionistDelete = nutritionistService.getById(id);
            if (!nutritionistDelete.isPresent())
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            nutritionistService.delete(id);
            return new ResponseEntity<Nutritionist>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Nutritionists", notes = "Método para listar todos los Nutritionists")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nutritionists encontrados"),
            @ApiResponse(code = 404, message = "Nutritionists no encontrados")
    })
    public ResponseEntity<List<Nutritionist>> findAll() {
        try {
            List<Nutritionist> nutritionists = nutritionistService.getAll();
            if (nutritionists.size() > 0)
                return new ResponseEntity<List<Nutritionist>>(nutritionists, HttpStatus.OK);
            else
                return new ResponseEntity<List<Nutritionist>>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<List<Nutritionist>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionist por Id", notes = "Método para encontrar un Nutritionist por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nutritionist encontrado"),
            @ApiResponse(code = 404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> findById(@PathVariable("id") Integer id) {
        try {
            Optional<Nutritionist> nutritionist = nutritionistService.getById(id);
            if (!nutritionist.isPresent())
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Nutritionist>(nutritionist.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionist por Username", notes = "Método para encontrar un Nutritionist por su respectivo Username")
    @ApiResponses({
            @ApiResponse(code=201, message = "Nutritionist encontrado"),
            @ApiResponse(code=404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> findByUsername(@PathVariable("username") String username){
        try{
            Nutritionist nutritionist = nutritionistService.findByUsername(username);
            if(nutritionist==null)
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Nutritionist>(nutritionist,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByCnpNumber/{cnp_number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionist por su CNP", notes = "Método para encontrar un Nutritionist por su respectivo CNP")
    @ApiResponses({
            @ApiResponse(code=201, message = "Nutritionist encontrado"),
            @ApiResponse(code=404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> findByCnpNumber(@PathVariable("cnp_number") Integer cnp_number){
        try{
            Nutritionist nutritionist = nutritionistService.findByCnpNumber(cnp_number);
            if(nutritionist==null)
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Nutritionist>(nutritionist, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="searchByFirstname/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionist por firstname", notes = "Método para encontrar Nutritionists por su respectivo firstname")
    @ApiResponses({
            @ApiResponse(code=201, message = "Nutritionists encontrados"),
            @ApiResponse(code=404, message = "Nutritionists no encontrados")
    })
    public ResponseEntity<List<Nutritionist>> findByFirstName(@PathVariable("firstname") String firstname){
        try{
            List<Nutritionist> nutritionists = nutritionistService.findByFirstName(firstname);
            if(nutritionists.size()>0)
                return new ResponseEntity<List<Nutritionist>>(nutritionists, HttpStatus.OK);
            else
                return new ResponseEntity<List<Nutritionist>>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<List<Nutritionist>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="searchByLastname/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionist por lastname", notes = "Método para encontrar Nutritionists por su respectivo lastname")
    @ApiResponses({
            @ApiResponse(code=201, message = "Nutritionists encontrados"),
            @ApiResponse(code=404, message = "Nutritionists no encontrados")
    })
    public ResponseEntity<List<Nutritionist>> findByLastName(@PathVariable("lastname") String lastname){
        try{
            List<Nutritionist> nutritionists = nutritionistService.findByLastName(lastname);
            if(nutritionists.size()>0)
                return new ResponseEntity<List<Nutritionist>>(nutritionists, HttpStatus.OK);
            else
                return new ResponseEntity<List<Nutritionist>>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<List<Nutritionist>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="searchByFirstnameAndLastname/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionists por firstname y lastname", notes = "Método para encontrar Nutritionists por su respectivo firstname y lastname")
    @ApiResponses({
            @ApiResponse(code=201, message = "Nutritionists encontrados"),
            @ApiResponse(code=404, message = "Nutritionists no encontrados")
    })
    public ResponseEntity<List<Nutritionist>> findByFirstnameAndLastname(
            @PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname){
        try{
            List<Nutritionist> nutritionists = nutritionistService.findByFirstNameAndLastName(firstname,lastname);
            if(nutritionists.size()>0)
                return new ResponseEntity<List<Nutritionist>>(nutritionists, HttpStatus.OK);
            else
                return new ResponseEntity<List<Nutritionist>>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<List<Nutritionist>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}