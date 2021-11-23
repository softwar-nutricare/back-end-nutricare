package com.appnutricare.service;

import com.appnutricare.entities.Bill;
import com.appnutricare.entities.Client;
import com.appnutricare.repository.IBillRepository;
import com.appnutricare.service.impl.BillServiceImpl;
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
public class BillServiceImplTest {
    @Mock
    private IBillRepository billRepository;
    @InjectMocks
    private BillServiceImpl billService;

    @Test
    public void saveTest(){
        Client client = new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        Bill bill = new Bill(1, client, ParseDate("2017-07-21"), 0.155, 1234567891);

        given(billRepository.save(bill)).willReturn(bill);

        Bill saveBill = null;

        try{
            saveBill = billService.save(bill);
        }catch (Exception e){
        }

        assertThat(saveBill).isNotNull();
        verify(billRepository).save(any(Bill.class));
    }

    @Test
    void findByIdTest() throws Exception {
        Integer id = 1;
        Client client = new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));
        Bill bill = new Bill(1, client, ParseDate("2017-07-21"), 0.155, 1234567891);

        given(billRepository.findById(id)).willReturn(Optional.of(bill));

        Optional<Bill> expected = billService.getById(id);

        assertThat(expected).isNotNull();
    }

    @Test
    void findAllTest() throws Exception {

        List<Bill> billList;

        Client client = new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));

        billList = new ArrayList<>();
        billList.add(new Bill(1, client, ParseDate("2017-07-21"), 0.155, 1234567891));
        billList.add(new Bill(1, client, ParseDate("2017-07-21"), 0.155, 1234567891));

        given(billRepository.findAll()).willReturn(billList);
        List<Bill> expected = billService.getAll();
        assertEquals(expected, billList);
    }

    @Test
    void findAllByClient() throws Exception {
        Integer clientId = 1;
        List<Bill> billList;

        Client client = new Client(1,"JosueCuentas1","123","Josue1",
                "Cuentas1","josue1.c1012gmail.com",ParseDate("2017-07-21 17:32:28"));

        billList = new ArrayList<>();
        billList.add(new Bill(1, client, ParseDate("2017-07-21"), 0.155, 1234567891));
        billList.add(new Bill(1, client, ParseDate("2017-07-21"), 0.155, 1234567891));

        given(billRepository.findAllByClient(clientId)).willReturn(billList);
        List<Bill> expected = billService.findAllByClient(clientId);
        assertThat(expected).isNotNull();
    }

    @Test
    void deleteTest() throws Exception {
        Integer id = 1;
        billService.delete(1);
        verify(billRepository, times(1)).deleteById(id);
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
