package com.appnutricare.service;

import com.appnutricare.entities.Nutritionist;
import com.appnutricare.entities.ProfessionalProfile;
import com.appnutricare.repository.INutritionistRepository;
import com.appnutricare.service.impl.NutritionistServiceImpl;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NutritionistServiceImplTest {

    @Mock
    private INutritionistRepository nutritionistRepository;

    @InjectMocks
    private NutritionistServiceImpl nutritionistService;

    @Test
    public void saveTest(){
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistRepository.save(nutritionist)).willReturn(nutritionist);

        Nutritionist savedNutritionist = null;
        try{
            savedNutritionist = nutritionistService.save(nutritionist);
        }catch (Exception e){
        }

        assertThat(savedNutritionist).isNotNull();
        verify(nutritionistRepository).save(any(Nutritionist.class));
    }

    @Test
    void deleteTest() throws Exception{
        Integer id = 1;
        nutritionistService.delete(id);
        verify(nutritionistRepository, times(1)).deleteById(id);
    }

    @Test
    void findByIdTest() throws Exception {
        Integer id = 1;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistRepository.findById(id)).willReturn(Optional.of(nutritionist));

        Optional<Nutritionist> expected = nutritionistService.getById(id);

        assertThat(expected).isNotNull();
    }

    @Test
    void findAllTest() throws Exception {
        List<Nutritionist> nutritionistList;
        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        given(nutritionistRepository.findAll()).willReturn(nutritionistList);
        List<Nutritionist> expected = nutritionistService.getAll();
        assertEquals(expected, nutritionistList);
    }

    @Test
    void findByUsernameTest() throws Exception {
        String Username = "pepito1";
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistRepository.findByUsername(Username)).willReturn(nutritionist);
        Nutritionist expected = nutritionistService.findByUsername(Username);
        assertThat(expected).isNotNull();
    }

    @Test
    void findByCnpNumberTest() throws Exception {
        Integer CnpNumber = 123456;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        given(nutritionistRepository.findByCnpNumber(CnpNumber)).willReturn(nutritionist);
        Nutritionist expected = nutritionistService.findByCnpNumber(CnpNumber);
        assertThat(expected).isNotNull();
    }

    @Test
    void findByFirstnameTest() throws Exception {
        String FirstName = "Jose1";
        List<Nutritionist> nutritionistList;

        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        given(nutritionistRepository.findByFirstName(FirstName)).willReturn(nutritionistList);
        List<Nutritionist> expected = nutritionistService.findByFirstName(FirstName);
        assertThat(expected).isNotNull();
    }

    @Test
    void findByLastnameTest() throws Exception {
        String LastName = "Perez1";
        List<Nutritionist> nutritionistList;

        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        given(nutritionistRepository.findByLastName(LastName)).willReturn(nutritionistList);
        List<Nutritionist> expected = nutritionistService.findByLastName(LastName);
        assertThat(expected).isNotNull();
    }

    @Test
    void findByFirstAndLastnameTest() throws Exception {
        String FirstName = "Jose1";
        String LastName = "Perez1";
        List<Nutritionist> nutritionistList;

        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        given(nutritionistRepository.findByFirstNameAndLastName(FirstName, LastName)).willReturn(nutritionistList);
        List<Nutritionist> expected = nutritionistService.findByFirstNameAndLastName(FirstName, LastName);
        assertThat(expected).isNotNull();
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
