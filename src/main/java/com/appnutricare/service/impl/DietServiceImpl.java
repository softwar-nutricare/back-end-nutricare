package com.appnutricare.service.impl;

import com.appnutricare.entities.Diet;
import com.appnutricare.repository.IDietRepository;
import com.appnutricare.service.IDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DietServiceImpl implements IDietService {

    @Autowired
    private IDietRepository dietRepository;

    @Override
    @Transactional
    public Diet save(Diet diet) throws Exception {
        return dietRepository.save(diet);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        dietRepository.deleteById(id);

    }

    @Override
    public List<Diet> getAll() throws Exception {
        return dietRepository.findAll();
    }

    @Override
    public Optional<Diet> getById(Integer id) throws Exception {
        return dietRepository.findById(id);
    }
}
