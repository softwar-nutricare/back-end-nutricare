package com.appnutricare.controller;


import com.appnutricare.entities.Nutritionist;
import com.appnutricare.entities.ProfessionalProfile;
import com.appnutricare.repository.IProfessionalSpecialtiesRepository;
import com.appnutricare.service.IRecipeService;
import com.appnutricare.service.ISpecialtyService;
import com.appnutricare.service.impl.ProfessionalProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfessionalProfileController.class)
@ActiveProfiles("test")
public class ProfessionalProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProfessionalProfileServiceImpl professionalProfileService;
    @MockBean
    private ISpecialtyService specialtyService;
    @MockBean
    private IProfessionalSpecialtiesRepository professionalSpecialtiesRepository;

    private List<ProfessionalProfile> profileList;

    private List<Nutritionist> nutritionistList;

    @BeforeEach
    void setUp(){
        profileList = new ArrayList<>();
        nutritionistList = new ArrayList<>();

        nutritionistList.add(new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(2, "pepito2", "pepito123",
                "Jose2", "Perez2", "pepito2@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(3, "pepito3", "pepito123",
                "Jose3", "Perez3", "pepito3@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000
        nutritionistList.add(new Nutritionist(4, "pepito4", "pepito123",
                "Jose4", "Perez4", "pepito4@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"))); //.10000

        profileList.add(new ProfessionalProfile(6,"Adolescentes con problemas alimenticios", nutritionistList.get(0)));
        profileList.add(new ProfessionalProfile(7,"Adolescentes con problemas alimenticios", nutritionistList.get(1)));
        profileList.add(new ProfessionalProfile(8,"Adolescentes con problemas alimenticios", nutritionistList.get(2)));
        profileList.add(new ProfessionalProfile(9,"Adolescentes con problemas alimenticios", nutritionistList.get(3)));
    }

    @Test
    void findAllProfessionalProfiles() throws Exception{
        given(professionalProfileService.getAll()).willReturn(profileList);
        mockMvc.perform(get("/api/ProfessionalProfiles")).andExpect(status().isOk());
    }

    @Test
    void findSpecialtyId() throws Exception{
        Integer ProfessionalId = 6;
        Nutritionist nutritionist = new Nutritionist(1, "pepito1", "pepito123",
                "Jose1", "Perez1", "pepito1@upc.edu.pe", 123456, ParseDate("2017-07-21 17:32:28"));
        ProfessionalProfile professionalProfile = new ProfessionalProfile(6,"\"Adolescentes con problemas alimenticios\"", nutritionist);

        given(professionalProfileService.getById(ProfessionalId)).willReturn(Optional.of(professionalProfile));

        mockMvc.perform(get("/api/ProfessionalProfiles/{id}", professionalProfile.getId())).andExpect(status().isOk());
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
