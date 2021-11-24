package com.appnutricare.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSpecialtiesFK implements Serializable {

    @Column(name = "professional_profile_id", nullable = false)
    private Integer professionalProfileId;

    @Column(name = "specialty_id", nullable = false)
    private Integer specialtyId;
}