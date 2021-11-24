package com.appnutricare.repository;

import com.appnutricare.entities.ClientFavoriteRecipes;
import com.appnutricare.entities.ClientFavoriteRecipesFK;

import com.appnutricare.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClientFavoriteRecipesRepository extends JpaRepository<ClientFavoriteRecipes, ClientFavoriteRecipesFK> {
    @Query("Select b.recipe from ClientFavoriteRecipes b where b.client.id = :client_id")
    public List<Recipe> findByClient(@Param("client_id") Integer client_id);
}
