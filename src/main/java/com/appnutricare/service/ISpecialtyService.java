package com.appnutricare.service;

import com.appnutricare.entities.Specialty;

import java.util.List;


public interface ISpecialtyService extends CrudService<Specialty>{

    public List<Specialty> findByInstitutionName(String institution_name) throws Exception;
    public Specialty findByNameAndInstitution(String name, String institution_name) throws Exception;
}
