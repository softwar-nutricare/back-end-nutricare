package com.appnutricare.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="client_favorite_recipes")
@NoArgsConstructor
@AllArgsConstructor
public class ClientFavoriteRecipes{

    @EmbeddedId
    private ClientFavoriteRecipesFK clientFavoriteRecipesFK;

    @ManyToOne
    @MapsId("clientId")
    private Client client;

    @ManyToOne
    @MapsId("recipeId")
    private Recipe recipe;

    @Column(name = "added_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedAt;
}