package com.appnutricare.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "professional_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalProfile implements Serializable {

    public ProfessionalProfile(Integer id, String professional_experience_description, Nutritionist nutritionist) {
        this.id = id;
        this.professional_experience_description = professional_experience_description;
        this.nutritionist = nutritionist;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="professional_experience_description", nullable = false, length = 500)
    private String professional_experience_description;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="nutritionist_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Nutritionist nutritionist;

    @OneToMany(mappedBy = "professionalProfile")
    private List<ProfessionalSpecialties> professionalProfileAssoc;
}
