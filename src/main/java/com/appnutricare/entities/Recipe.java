package com.appnutricare.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Customer.findByFirstName",query = "select  r from Recipe r where r.name= ?1")
public class Recipe implements Serializable {

        public Recipe(Integer id, String name, String descripcion, String preparation, String ingredients, Integer favorite, Date created_at, Date last_modification, Nutritionist nutritionist) {
                this.id = id;
                this.name = name;
                this.descripcion = descripcion;
                this.preparation = preparation;
                this.ingredients = ingredients;
                this.favorite = favorite;
                this.created_at = created_at;
                this.last_modification = last_modification;
                this.nutritionist = nutritionist;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name="name", nullable = false, length = 50)
        private String name;
        @Column(name="description", nullable = false, length = 250)
        private String descripcion;
        @Column(name="preparation", nullable = false, length = 500)
        private String preparation;
        @Column(name="ingredients", nullable = false, length = 500)
        private String ingredients;
        @Column(name="favorite", nullable = true)
        private Integer favorite;
        @Column(name="created_at", nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date created_at;
        @Column(name="last_modification", nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date last_modification;

        //Implementacion de FK - el many to one con nutritionist
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="nutritionist_id", nullable = false)
        @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
        private Nutritionist nutritionist;

        @OneToMany(mappedBy = "recipe")
        private List<ClientFavoriteRecipes> recipeClientAssoc;

        @OneToMany(mappedBy = "recipe")
        private List<DietRecipes> recipeDietAssoc;
}
