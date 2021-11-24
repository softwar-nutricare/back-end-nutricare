package com.appnutricare.repository;

import com.appnutricare.entities.DietRecipes;
import com.appnutricare.entities.DietRecipesFK;
import com.appnutricare.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDietRecipesRepository extends JpaRepository<DietRecipes, DietRecipesFK> {
    @Query("Select b.recipe from DietRecipes b where b.diet.id = :diet_id")
    public List<Recipe> findByDiet(@Param("diet_id") Integer diet_id);
}
