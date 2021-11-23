package com.appnutricare.service;


import com.appnutricare.entities.Specialty;
import com.appnutricare.repository.ISpecialtyRepository;
import com.appnutricare.service.impl.SpecialtyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SpecialtyServiceImplTest {

    @Mock
    private ISpecialtyRepository specialtyRepository;
    @InjectMocks
    private SpecialtyServiceImpl specialtyService;


    @Test
    public void saveTest(){
        Specialty specialty = new Specialty(6,"Adolescentes","UCV");

        given(specialtyRepository.save(specialty)).willReturn(specialty);

        Specialty savedSpecialty = null;
        try {
            savedSpecialty = specialtyService.save(specialty);
        } catch (Exception e) {
        }

        assertThat(savedSpecialty).isNotNull();
        verify(specialtyRepository).save(any(Specialty.class));
    }

    @Test
    void findByIdTest() throws  Exception{
        Integer id = 6;
        Specialty specialty = new Specialty(6,"Adolescentes","UCV");

        given(specialtyRepository.findById(id)).willReturn(Optional.of(specialty));

        Optional<Specialty> expected = specialtyService.getById(id);
        assertThat(expected).isNotNull();

    }
    @Test
    void findAllTest() throws  Exception{
        List<Specialty> list = new ArrayList<>();
        list.add(new Specialty(6,"Adolescentes","UCV"));
        list.add(new Specialty(7,"Adolescentes","UCV"));
        list.add(new Specialty(9,"Adolescentes","UCV"));
        list.add(new Specialty(11,"Adolescentes","UCV"));
        list.add(new Specialty(21,"Adolescentes","UCV"));
        list.add(new Specialty(22,"Adolescentes","UCV"));
        list.add(new Specialty(41,"Adolescentes","UCV"));

        given(specialtyRepository.findAll()).willReturn(list);
        List<Specialty> expected = specialtyService.getAll();
        assertEquals(expected,list);
    }

    @Test
    void deleteTest() throws  Exception {
        Integer id = 6;
        specialtyService.delete(id);
        specialtyService.delete(id);
        verify(specialtyRepository, times(2)).deleteById(id);
    }
}
