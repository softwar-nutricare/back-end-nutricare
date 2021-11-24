package com.appnutricare.service;

import com.appnutricare.entities.Appointment;
import com.appnutricare.entities.Diet;

import java.util.Date;
import java.util.List;

public interface IAppointmentService extends CrudService<Appointment> {
    public List<Appointment> findBetweenDates(Date date1, Date date2) throws Exception;
    public List<Appointment> findByClient(Integer client_id) throws Exception;
    public List<Appointment> findByNutritionist(Integer nutritionist_id) throws Exception;
}
