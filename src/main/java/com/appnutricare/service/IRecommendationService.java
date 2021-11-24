package com.appnutricare.service;


import com.appnutricare.entities.Appointment;
import com.appnutricare.entities.Recommendation;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Date;


public interface IRecommendationService extends CrudService<Recommendation>{
    public List<Recommendation> findByName(String name) throws Exception;
    public List<Recommendation> findBetweenDates(Date date1, Date date2) throws Exception;
    public List<Recommendation> findByNutritionist(Integer nutritionist_id) throws Exception;
}