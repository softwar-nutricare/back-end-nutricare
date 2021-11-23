package com.appnutricare.controller;

import com.appnutricare.entities.Specialty;
import com.appnutricare.service.impl.SpecialtyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SpecialtyController.class)
@ActiveProfiles("test")
public class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpecialtyServiceImpl specialtyService;

    private List<Specialty> specialtyList;

    @BeforeEach
    void setUp(){
        specialtyList = new ArrayList<>();
        specialtyList.add(new Specialty(6,"Adolescentes","UCV"));
        specialtyList.add(new Specialty(7,"Adolescentes","UCV"));
        specialtyList.add(new Specialty(8,"Adolescentes","UCV"));
        specialtyList.add(new Specialty(9,"Adolescentes","UCV"));
    }

    @Test
    void findAllSpecialties() throws Exception{
        given(specialtyService.getAll()).willReturn(specialtyList);
        mockMvc.perform(get("/api/specialty")).andExpect(status().isOk());
    }

    @Test
    void findSpecialtyId() throws Exception{
        Integer SpecyaltyId = 6;
        Specialty specialty = new Specialty(6,"Adolescentes","UCV");

        given(specialtyService.getById(SpecyaltyId)).willReturn(Optional.of(specialty));

        mockMvc.perform(get("/api/specialty/{id}", specialty.getId())).andExpect(status().isOk());
    }

}
