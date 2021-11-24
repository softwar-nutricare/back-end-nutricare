package com.appnutricare.repository;

import com.appnutricare.entities.Diet;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IDietRepository extends JpaRepository<Diet, Integer> {

}
