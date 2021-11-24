package com.appnutricare.service.impl;

import com.appnutricare.entities.Specialty;
import com.appnutricare.repository.ISpecialtyRepository;
import com.appnutricare.service.ISpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpecialtyServiceImpl implements ISpecialtyService {

    @Autowired
    private ISpecialtyRepository specialtyRepository;

    @Override
    @Transactional
    public Specialty save(Specialty specialty) throws Exception {
        return specialtyRepository.save(specialty);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        specialtyRepository.deleteById(id);
    }

    @Override
    public List<Specialty> getAll() throws Exception {
        return specialtyRepository.findAll();
    }

    @Override
    public Optional<Specialty> getById(Integer id) throws Exception {
        return specialtyRepository.findById(id);
    }

    @Override
    public List<Specialty> findByInstitutionName(String institution_name) throws Exception {
        return specialtyRepository.findByInstitutionName(institution_name);
    }

    @Override
    public Specialty findByNameAndInstitution(String name, String institution_name) throws Exception{
        return specialtyRepository.findByNameAndInstitutionName(name, institution_name);
    }
}
