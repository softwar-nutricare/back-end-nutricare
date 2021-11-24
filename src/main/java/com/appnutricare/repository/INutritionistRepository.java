package com.appnutricare.repository;

import com.appnutricare.entities.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INutritionistRepository extends JpaRepository<Nutritionist, Integer> {
    public Nutritionist findByUsername(String username);
    public Nutritionist findByCnpNumber(Integer cnp_number);
    public List<Nutritionist> findByFirstName(String firstname);
    public List<Nutritionist> findByLastName(String lastname);
    public List<Nutritionist> findByFirstNameAndLastName(String firstname, String lastname);
}
