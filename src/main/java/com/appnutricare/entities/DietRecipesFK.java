package com.appnutricare.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DietRecipesFK implements Serializable {

    @Column(name = "diet_id", nullable = false)
    private Integer dietId;

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;
}