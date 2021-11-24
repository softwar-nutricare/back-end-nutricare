package com.appnutricare.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "specialty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialty implements Serializable {

    public Specialty(Integer id, String name, String institutionName) {
        this.id = id;
        this.name = name;
        this.institutionName = institutionName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name ="name", nullable = false, length = 50)
    private String name;
    @Column(name ="institution_name", nullable = false, length = 50)
    private String institutionName;

    @OneToMany(mappedBy = "specialty")
    private List<ProfessionalSpecialties> specialtyAssoc;
}
