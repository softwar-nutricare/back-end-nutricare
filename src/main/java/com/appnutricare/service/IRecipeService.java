package com.appnutricare.service;

import com.appnutricare.entities.Recipe;

import java.util.Date;
import java.util.List;

public interface IRecipeService extends CrudService<Recipe>{
    public List<Recipe> findAllByNutritionist(Integer nutritionist_id) throws Exception;
    public Recipe findByName(String name) throws Exception;
    public List<Recipe> findBetweenDates(Date date1, Date date2) throws Exception;
}
