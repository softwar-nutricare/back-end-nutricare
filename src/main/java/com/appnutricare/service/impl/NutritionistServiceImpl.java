package com.appnutricare.service.impl;

import com.appnutricare.entities.Nutritionist;
import com.appnutricare.repository.INutritionistRepository;
import com.appnutricare.service.INutritionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class NutritionistServiceImpl implements INutritionistService {
    @Autowired
    private INutritionistRepository nutritionistRepository;

    @Override
    @Transactional
    public Nutritionist save(Nutritionist nutritionist) throws Exception {
        return nutritionistRepository.save(nutritionist);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        nutritionistRepository.deleteById(id);
    }

    @Override
    public List<Nutritionist> getAll() throws Exception {
        return nutritionistRepository.findAll();
    }

    @Override
    public Optional<Nutritionist> getById(Integer id) throws Exception {
        return nutritionistRepository.findById(id);
    }

    @Override
    public Nutritionist findByUsername(String username) throws Exception {
        return nutritionistRepository.findByUsername(username);
    }

    @Override
    public Nutritionist findByCnpNumber(Integer cnp_number) throws Exception {
        return nutritionistRepository.findByCnpNumber(cnp_number);
    }

    @Override
    public List<Nutritionist> findByFirstName(String firstname) throws Exception {
        return nutritionistRepository.findByFirstName(firstname);
    }

    @Override
    public List<Nutritionist> findByLastName(String lastname) throws Exception {
        return nutritionistRepository.findByLastName(lastname);
    }

    @Override
    public List<Nutritionist> findByFirstNameAndLastName(String firstname, String lastname) throws Exception {
        return nutritionistRepository.findByFirstNameAndLastName(firstname, lastname);
    }
}
