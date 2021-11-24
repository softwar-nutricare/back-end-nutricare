package com.appnutricare.controller;


import com.appnutricare.entities.*;
import com.appnutricare.repository.IProfessionalSpecialtiesRepository;
import com.appnutricare.service.IProfessionalProfileService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ProfessionalProfiles")
@Api(tags = "Profesional Profile", value = "Servicio Web RESTful de Profesional_profiles")
public class ProfessionalProfileController {

    @Autowired
    private IProfessionalProfileService professionalProfileService;
    @Autowired
    private ISpecialtyService specialtyService;
    @Autowired
    private IProfessionalSpecialtiesRepository professionalSpecialtiesRepository;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Profesional Profiles", notes = "Método para encontrar Profesional Profiles ")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Profesional Profiles encontrados"),
            @ApiResponse(code = 404, message = "Profesional Profiles no encontrados")
    })
    public ResponseEntity<List<ProfessionalProfile>>findAll(){
        try {
            List<ProfessionalProfile> professionalProfiles = new ArrayList<>();
            professionalProfiles = professionalProfileService.getAll();
            return new ResponseEntity<List<ProfessionalProfile>>(professionalProfiles, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<List<ProfessionalProfile>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Profesional Profile por Id", notes = "Método para encontrar Profesional Profile por Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Profesional Profile encontrado"),
            @ApiResponse(code = 404, message = "Profesional Profile no encontrado")
    })
    public ResponseEntity<ProfessionalProfile> findById(@PathVariable("id") Integer id){
        try {
            Optional<ProfessionalProfile> professionalProfile = professionalProfileService.getById(id);
            if(!professionalProfile.isPresent())
                return new ResponseEntity<ProfessionalProfile>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<ProfessionalProfile>(professionalProfile.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Profesional Profile", notes = "Método para agregar un Profesional Profile")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Profesional Profile agregado"),
            @ApiResponse(code = 404, message = "Profesional Profile no fue agregado")
    })
    public ResponseEntity<ProfessionalProfile> insertProfessionalProfile(@Valid @RequestBody ProfessionalProfile professionalProfile){
        try {
            ProfessionalProfile professionalProfileNew = professionalProfileService.save(professionalProfile);
            return ResponseEntity.status(HttpStatus.CREATED).body(professionalProfileNew);
        }catch (Exception e){
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualizar un Profesional Profile", notes = "Método para actualizar un Profesional Profile")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Profesional Profile actualizado"),
            @ApiResponse(code = 404, message = "Profesional Profile no encontrado")
    })
    public ResponseEntity<ProfessionalProfile> updateProfessionalProfile(@PathVariable("id") Integer id, @Valid @RequestBody ProfessionalProfile professionalProfile){
        try {
            Optional<ProfessionalProfile> professionalProfileOptional = professionalProfileService.getById(id);
            if(!professionalProfileOptional.isPresent())
                return new ResponseEntity<ProfessionalProfile>(HttpStatus.NOT_FOUND);
            professionalProfile.setId(id);
            professionalProfileService.save(professionalProfile);
            return new ResponseEntity<ProfessionalProfile>(professionalProfile,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Profesional Profile", notes = "Método para eliminar un Profesional Profile")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Profesional Profile eliminado"),
            @ApiResponse(code = 404, message = "Profesional Profile no encontrado")
    })
    public ResponseEntity<ProfessionalProfile> deleteDiet(@PathVariable("id") Integer id){
        try {
            Optional<ProfessionalProfile> professionalProfileOptional = professionalProfileService.getById(id);
            if(!professionalProfileOptional.isPresent())
                return new ResponseEntity<ProfessionalProfile>(HttpStatus.NOT_FOUND);
            professionalProfileService.delete(id);
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{specialty_id}/{professional_profile_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adición de Specialty a un ProfessionalProfile", notes = "Método que añade un Specialty a un ProfessionalProfile")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Specialty añadida al ProfessionalProfile"),
            @ApiResponse(code = 404, message = "Specialty o ProfessionalProfile no encontrado")
    })
    public ResponseEntity<ProfessionalSpecialties> addSpecialtyToProfessionalProfile(@PathVariable("specialty_id") Integer specialty_id,
                                                                   @PathVariable("professional_profile_id") Integer professional_profile_id){
        try {
            Optional<Specialty> foundSpecialty = specialtyService.getById(specialty_id);
            Optional<ProfessionalProfile> foundProfessionalProfile = professionalProfileService.getById(professional_profile_id);
            if(!foundSpecialty.isPresent())
                return new ResponseEntity<ProfessionalSpecialties>(HttpStatus.NOT_FOUND);
            if(!foundProfessionalProfile.isPresent())
                return new ResponseEntity<ProfessionalSpecialties>(HttpStatus.NOT_FOUND);
            ProfessionalSpecialtiesFK newFKS = new ProfessionalSpecialtiesFK(professional_profile_id, specialty_id);
            ProfessionalSpecialties professionalSpecialties = new ProfessionalSpecialties(newFKS, foundProfessionalProfile.get(), foundSpecialty.get());

            professionalSpecialtiesRepository.save(professionalSpecialties);
            return ResponseEntity.status(HttpStatus.CREATED).body(professionalSpecialties);
        }catch (Exception e){
            return new ResponseEntity<ProfessionalSpecialties>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findSpecialtiesByProfessionalProfileId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Specialties de un ProfessionalProfile", notes = "Método para listar Specialties de un ProfessionalProfile")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialties encontrados"),
            @ApiResponse(code = 404, message = "Specialties no encontrados")
    })
    public ResponseEntity<List<Specialty>> findSpecialtiesByProfessionalProfileId(@PathVariable("id") Integer id)
    {
        try {
            List<Specialty> specialties = new ArrayList<>();
            specialties = professionalSpecialtiesRepository.findByProfessionalProfileId(id);
            if(specialties.isEmpty())
                return new ResponseEntity<List<Specialty>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Specialty>>(specialties, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Specialty>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{specialty_id}/{professional_profile_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Specialty de un ProfessionalProfile", notes = "Método para eliminar un Specialty de un ProfessionalProfile")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Specialty eliminado"),
            @ApiResponse(code = 404, message = "Specialty no encontrado")
    })
    public ResponseEntity<ProfessionalProfile> deleteSpecialtyFromProfessionalProfile(@PathVariable("specialty_id") Integer specialty_id,
                                                     @PathVariable("professional_profile_id") Integer professional_profile_id)
    {
        try{
            ProfessionalSpecialtiesFK newFKS = new ProfessionalSpecialtiesFK(professional_profile_id, specialty_id);
            Optional<ProfessionalSpecialties> professionalSpecialties = professionalSpecialtiesRepository.findById(newFKS);
            if(!professionalSpecialties.isPresent())
                return new ResponseEntity<ProfessionalProfile>(HttpStatus.NOT_FOUND);
            professionalSpecialtiesRepository.delete(professionalSpecialties.get());
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByNutritionist/{nutritionist_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Professional Profile por nutritionist id", notes = "Método para encontrar Professional Profile por nutritionist id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Professional Profile encontrado"),
            @ApiResponse(code = 404, message = "Professional Profile no ubicado")
    })
    public ResponseEntity<ProfessionalProfile> findByNutritionist(@PathVariable("nutritionist_id") Integer nutritionist_id)
    {
        try{
            ProfessionalProfile professionalProfile = professionalProfileService.findByNutritionist(nutritionist_id);
            if(professionalProfile == null)
                return new ResponseEntity<ProfessionalProfile>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<ProfessionalProfile>(professionalProfile, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ProfessionalProfile>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
