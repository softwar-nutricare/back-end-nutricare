package com.appnutricare.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="professional_specialties")
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSpecialties {

    @EmbeddedId
    private ProfessionalSpecialtiesFK professionalSpecialtiesFK;

    @ManyToOne
    @MapsId("professionalProfileId")
    private ProfessionalProfile professionalProfile;

    @ManyToOne
    @MapsId("specialtyId")
    private Specialty specialty;
}
