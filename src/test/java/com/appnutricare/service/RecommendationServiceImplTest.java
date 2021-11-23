package com.appnutricare.service;
import com.appnutricare.entities.*;
import com.appnutricare.repository.IRecommendationRepository;
import com.appnutricare.service.impl.RecommendationServiceImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceImplTest {
    @Mock
    private IRecommendationRepository recommendationRepository;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Test
    public void saveTest()
    {
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));
        Recommendation recommendation = new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionist);
        given(recommendationRepository.save(recommendation)).willReturn(recommendation);

        Recommendation savedRecommendation = null;
        try{
            savedRecommendation = recommendationService.save(recommendation);
        }catch (Exception e)
        {
        }

        assertThat(savedRecommendation).isNotNull();
        verify(recommendationRepository).save(any(Recommendation.class));
    }

    @Test
    void deleteTest() throws Exception{
        Integer id = 1;
        recommendationService.delete(id);
        verify(recommendationRepository, times(1)).deleteById(id);
    }

    @Test
    void findByIdTest() throws Exception
    {
        Integer recommendationId = 1;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Recommendation recommendation = new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionist);
        given(recommendationService.getById(recommendationId)).willReturn(Optional.of(recommendation));

        Optional<Recommendation> expected = recommendationService.getById(recommendationId);
        assertThat(expected).isNotNull();
    }

    @Test
    void findAllTest() throws Exception
    {
        List<Nutritionist> nutritionistList;
        List<Recommendation> recommendationList;

        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        recommendationList = new ArrayList<>();
        recommendationList.add(new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(0)));
        recommendationList.add(new Recommendation(2,"pera2","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(1)));
        recommendationList.add(new Recommendation(3,"pera3","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(2)));
        recommendationList.add(new Recommendation(4,"pera4","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(3)));

        given(recommendationRepository.findAll()).willReturn(recommendationList);
        List<Recommendation> expected = recommendationService.getAll();
        assertEquals(expected, recommendationList);
    }

    public static Date ParseDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy,MM,dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }
    @Test
    void findByNameTest() throws Exception {
        String name = "pera";
        List<Nutritionist> nutritionistList;
        List<Recommendation> recommendationList;

        nutritionistList = new ArrayList<>();
        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        recommendationList = new ArrayList<>();
        recommendationList.add(new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(0)));
        recommendationList.add(new Recommendation(2,"pera2","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(1)));
        recommendationList.add(new Recommendation(3,"pera3","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(2)));
        recommendationList.add(new Recommendation(4,"pera4","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionistList.get(3)));

        given(recommendationRepository.findByName(name)).willReturn(recommendationList);
        List<Recommendation> expected = recommendationService.findByName(name);
        assertThat(expected).isNotNull();
    }

    @Test
    void findBetweenDatesTest() throws Exception{
        String date1_string = "2015-07-21 17:32:28";
        String date2_string = "2022-07-21 17:32:28";
        Date date1 = ParseDate2(date1_string);
        Date date2 = ParseDate2(date2_string);
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28")); //.10000
        Recommendation recommendation = new Recommendation(1,"pera","descripcionRandom1",
                ParseDate("2017-07-21 17:32:28"),ParseDate("2017-07-21 17:32:28"), nutritionist);
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(recommendation);
        given(recommendationRepository.findBetweenDates(date1, date2)).willReturn(recommendations);
        List<Recommendation> expected = recommendationService.findBetweenDates(date1, date2);

        assertThat(expected).isNotNull();
    }

    public static Date ParseDate2(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }

}