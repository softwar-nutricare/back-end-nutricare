package com.appnutricare.service.impl;


import com.appnutricare.entities.Appointment;
import com.appnutricare.entities.Recommendation;
import com.appnutricare.repository.IRecommendationRepository;
import com.appnutricare.service.IRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RecommendationServiceImpl implements IRecommendationService{

    @Autowired
    private IRecommendationRepository recommendationRepository;

    @Override
    @Transactional
    public Recommendation save(Recommendation recommendation) throws Exception {
        return recommendationRepository.save(recommendation);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        recommendationRepository.deleteById(id);
    }

    @Override
    public List<Recommendation> getAll() throws Exception {
        return recommendationRepository.findAll();
    }

    @Override
    public Optional<Recommendation> getById(Integer id) throws Exception {
        return recommendationRepository.findById(id);
    }

    @Override
    public List<Recommendation> findByName(String name) throws Exception
    {
        return recommendationRepository.findByName(name);
    }

    @Override
    public List<Recommendation> findBetweenDates(Date date1, Date date2) throws Exception {
        return recommendationRepository.findBetweenDates(date1, date2);
    }

    @Override
    public List<Recommendation> findByNutritionist(Integer nutritionist_id) throws Exception
    {
        return recommendationRepository.findByNutritionist(nutritionist_id);
    }
}