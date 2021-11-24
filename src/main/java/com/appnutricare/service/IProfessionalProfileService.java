package com.appnutricare.service;

import com.appnutricare.entities.PaymentMethod;
import com.appnutricare.entities.ProfessionalProfile;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProfessionalProfileService extends CrudService<ProfessionalProfile>{
    public ProfessionalProfile findByNutritionist(Integer nutritionist_id) throws Exception;
}
