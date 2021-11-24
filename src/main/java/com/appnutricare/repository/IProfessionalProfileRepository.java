package com.appnutricare.repository;

import com.appnutricare.entities.Appointment;
import com.appnutricare.entities.ProfessionalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface IProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Integer> {
    @Query("Select b from ProfessionalProfile b where b.nutritionist.id = :nutritionist_id")
    public ProfessionalProfile findByNutritionist(@Param("nutritionist_id") Integer nutritionist_id);
}
