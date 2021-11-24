package com.appnutricare.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ClientFavoriteRecipesFK implements Serializable {

    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;
}