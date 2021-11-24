package com.appnutricare.controller;


import com.appnutricare.entities.Specialty;
import com.appnutricare.service.ISpecialtyService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/specialty")
@Api(tags = "Specialty", value = "Servicio Web RESTful de Specialtys")
public class SpecialtyController {

    @Autowired
    private ISpecialtyService specialtyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar todos los Specialtys", notes = "Método para encontrar Specialty")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialtys encontrados"),
            @ApiResponse(code = 404, message = "Specialtys no encontrados")
    })
    public ResponseEntity<List<Specialty>> findAll(){
        try {
            List<Specialty> specialties = new ArrayList<>();
            specialties = specialtyService.getAll();
            return new ResponseEntity<List<Specialty>>(specialties, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<List<Specialty>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Specialty por Id", notes = "Método para encontrar Specialty por Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialty encontrado"),
            @ApiResponse(code = 404, message = "Specialty no encontrado")
    })
    public ResponseEntity<Specialty> findById(@PathVariable("id") Integer id){
        try {
            Optional<Specialty> specialty = specialtyService.getById(id);
            if(!specialty.isPresent())
                return new ResponseEntity<Specialty>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Specialty>(specialty.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Specialty>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Specialty", notes = "Método para agregar un Specialty")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialty agregado"),
            @ApiResponse(code = 404, message = "Specialty no fue agregado")
    })
    public ResponseEntity<Specialty> insertSpecialty(@Valid @RequestBody Specialty specialty){
        try {
            Specialty specialtyNew = specialtyService.save(specialty);
            return ResponseEntity.status(HttpStatus.CREATED).body(specialtyNew);
        }catch (Exception e){
            return new ResponseEntity<Specialty>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de un Specialty", notes = "Método para actualizar un Specialty")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Specialty actualizado"),
            @ApiResponse(code = 404, message = "Specialty no encontrado")
    })
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable("id") Integer id, @Valid @RequestBody Specialty specialty){
        try {
            Optional<Specialty> specialtyOptional = specialtyService.getById(id);
            if(!specialtyOptional.isPresent())
                return new ResponseEntity<Specialty>(HttpStatus.NOT_FOUND);
            specialty.setId(id);
            specialtyService.save(specialty);
            return new ResponseEntity<Specialty>(specialty, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Specialty>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Specialty", notes = "Método para eliminar un Specialty")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Specialty eliminado"),
            @ApiResponse(code = 404, message = "Specialty no encontrado")
    })
    public ResponseEntity<Specialty> deleteSpecialty(@PathVariable("id") Integer id){
        try {
            Optional<Specialty> specialtyOptional = specialtyService.getById(id);
            if(!specialtyOptional.isPresent())
                return new ResponseEntity<Specialty>(HttpStatus.NOT_FOUND);
            specialtyService.delete(id);
            return new ResponseEntity<Specialty>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Specialty>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findByInstitutionName/{institution_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Specialty por institution_name", notes = "Método para encontrar Specialty por institution_name")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialty encontrado"),
            @ApiResponse(code = 404, message = "Specialty no encontrado")
    })
    public ResponseEntity<List<Specialty>> findByInstitutionName(@PathVariable("institution_name") String institution_name){
        try {
            List<Specialty> specialties = new ArrayList<>();
            specialties = specialtyService.findByInstitutionName(institution_name);
            if(specialties.isEmpty())
                return new ResponseEntity<List<Specialty>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Specialty>>(specialties, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Specialty>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findByNameAndInstitutionName/{name}/{nameInstitution}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Specialty por Name e Institution name", notes = "Método para encontrar Specialty por Name e Institution name")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialty encontrado"),
            @ApiResponse(code = 404, message = "Specialty no encontrado")
    })
    public ResponseEntity<Specialty> findByNameAndInstitutionName(@PathVariable("name") String name, @PathVariable("nameInstitution") String nameInstitution)
    {
        try {
            Specialty specialty = specialtyService.findByNameAndInstitution(name, nameInstitution);
            if(specialty == null)
                return new ResponseEntity<Specialty>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Specialty>(specialty, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Specialty>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
