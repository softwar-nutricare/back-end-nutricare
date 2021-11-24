package com.appnutricare.service.impl;

import com.appnutricare.entities.ProfessionalProfile;
import com.appnutricare.repository.IProfessionalProfileRepository;
import com.appnutricare.service.IProfessionalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfessionalProfileServiceImpl implements IProfessionalProfileService {

    @Autowired
    private IProfessionalProfileRepository professionalProfileRepository;

    @Override
    @Transactional
    public ProfessionalProfile save(ProfessionalProfile professionalprofile) throws Exception {
        return professionalProfileRepository.save(professionalprofile);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        professionalProfileRepository.deleteById(id);

    }

    @Override
    public List<ProfessionalProfile> getAll() throws Exception {
        return professionalProfileRepository.findAll();
    }

    @Override
    public Optional<ProfessionalProfile> getById(Integer id) throws Exception {
        return professionalProfileRepository.findById(id);
    }

    @Override
    public ProfessionalProfile findByNutritionist(Integer nutritionist_id) throws Exception{
        return professionalProfileRepository.findByNutritionist(nutritionist_id);
    }
}
