package com.appnutricare.service;



import com.appnutricare.entities.Nutritionist;
import com.appnutricare.entities.ProfessionalProfile;
import com.appnutricare.entities.Specialty;
import com.appnutricare.repository.IProfessionalProfileRepository;
import com.appnutricare.service.impl.ProfessionalProfileServiceImpl;
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
public class ProfessionalProfileServiceImplTest {

    @Mock
    private IProfessionalProfileRepository professionalProfileRepository;
    @InjectMocks
    private ProfessionalProfileServiceImpl professionalProfileService;

    @Test
    public void saveTest(){

        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));
        ProfessionalProfile professionalProfile = new ProfessionalProfile(6,"Adolescentes con problemas alimenticios", nutritionist);

        given(professionalProfileRepository.save(professionalProfile)).willReturn(professionalProfile);

        ProfessionalProfile savedProfessionalprofile = null;
        try {
            savedProfessionalprofile = professionalProfileService.save(professionalProfile);
        } catch (Exception e) {
        }

        assertThat(savedProfessionalprofile).isNotNull();
        verify(professionalProfileRepository).save(any(ProfessionalProfile.class));
    }


    @Test
    void findByIdTest() throws  Exception{
        Integer id = 6;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));
        ProfessionalProfile professionalProfile = new ProfessionalProfile(6,"Adolescentes con problemas alimenticios", nutritionist);

        given(professionalProfileRepository.findById(id)).willReturn(Optional.of(professionalProfile));

        Optional<ProfessionalProfile> expected = professionalProfileService.getById(id);
        assertThat(expected).isNotNull();

    }
    @Test
    void findAllTest() throws  Exception{

        List<Nutritionist> nutritionistList = new ArrayList<>();

        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        List<ProfessionalProfile> list = new ArrayList<>();
        list.add(new ProfessionalProfile(1,"Adolescentes con problemas alimenticios", nutritionistList.get(0)));
        list.add(new ProfessionalProfile(2,"Adolescentes con problemas alimenticios", nutritionistList.get(1)));
        list.add(new ProfessionalProfile(3,"Adolescentes con problemas alimenticios", nutritionistList.get(2)));
        list.add(new ProfessionalProfile(4,"Adolescentes con problemas alimenticios", nutritionistList.get(3)));

        given(professionalProfileRepository.findAll()).willReturn(list);
        List<ProfessionalProfile> expected = professionalProfileService.getAll();
        assertEquals(expected, list);

    }
    @Test
    void deleteTest() throws  Exception {
        Integer id = 6;
        professionalProfileService.delete(id);
        verify(professionalProfileRepository, times(1)).deleteById(id);
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
