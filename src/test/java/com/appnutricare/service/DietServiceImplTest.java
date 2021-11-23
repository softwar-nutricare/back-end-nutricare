package com.appnutricare.service;



import com.appnutricare.entities.Diet;
import com.appnutricare.repository.IDietRepository;
import com.appnutricare.service.impl.DietServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DietServiceImplTest {

    @Mock
    private IDietRepository dietRepository;
    @InjectMocks
    private DietServiceImpl dietService;


    @Test
    public void saveTest(){

        Diet diet = new Diet(1,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28"));

        given(dietRepository.save(diet)).willReturn(diet);

        Diet savedDiet = null;
        try {
            savedDiet = dietService.save(diet);
        } catch (Exception e) {
        }

        assertThat(savedDiet).isNotNull();
        verify(dietRepository).save(any(Diet.class));

    }

    @Test
    void findByIdTest() throws  Exception{
        Integer id = 1;
        Diet diet = new Diet(1,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28"));

        given(dietRepository.findById(id)).willReturn(Optional.of(diet));

        Optional<Diet> expected = dietService.getById(id);
        assertThat(expected).isNotNull();
    }
    @Test
    void findAllTest() throws  Exception{

        List<Diet> list = new ArrayList<>();
        list.add(new Diet(1,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));
        list.add(new Diet(3,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));
        list.add(new Diet(2,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));
        list.add(new Diet(4,"Dieta de pollo","Consiste en pollo frito", ParseDate("2017-07-21 17:32:28")));

        given(dietRepository.findAll()).willReturn(list);
        List<Diet> expected = dietService.getAll();
        assertEquals(expected, list);


    }
    @Test
    void deleteTest() throws  Exception {

        Integer id = 6;
        dietService.delete(id);
        dietService.delete(id);
        verify(dietRepository, times(2)).deleteById(id);


    }

    public static Date ParseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }
}
