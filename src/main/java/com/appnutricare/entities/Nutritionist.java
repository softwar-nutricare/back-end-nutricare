package com.appnutricare.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="nutritionist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nutritionist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username", nullable = false, length = 16)
    private String username;

    @Column(name="password", nullable = false, length = 50)
    private String password;

    @Column(name="first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name="email", nullable = false, length = 50)
    private String email;

    @Column(name="cnp_number", nullable = false, length = 6)
    private Integer cnpNumber;

    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
