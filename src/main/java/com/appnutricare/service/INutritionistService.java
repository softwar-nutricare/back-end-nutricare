package com.appnutricare.service;

import com.appnutricare.entities.Nutritionist;

import java.util.List;

public interface INutritionistService extends CrudService<Nutritionist> {
    public Nutritionist findByUsername(String username) throws Exception;
    public Nutritionist findByCnpNumber(Integer cnp_number) throws Exception;
    public List<Nutritionist> findByFirstName(String firstname) throws Exception;
    public List<Nutritionist> findByLastName(String lastname) throws Exception;
    public List<Nutritionist> findByFirstNameAndLastName(String firstname, String lastname) throws Exception;
}