package com.appnutricare.service;

import com.appnutricare.entities.Bill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IBillService extends CrudService<Bill>{
    public List<Bill> findAllByClient(Integer client_id) throws Exception;

    public List<Bill> findBetweenDates(Date date1, Date date2) throws Exception;
}
