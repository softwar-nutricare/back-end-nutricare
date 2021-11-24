package com.appnutricare.service.impl;

import com.appnutricare.entities.Appointment;
import com.appnutricare.repository.IAppointmentRepository;
import com.appnutricare.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private IAppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public Appointment save(Appointment appointment) throws Exception {
        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAll() throws Exception {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getById(Integer id) throws Exception {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> findBetweenDates(Date date1, Date date2) throws Exception {
        return appointmentRepository.findBetweenDates(date1, date2);
    }

    @Override
    public List<Appointment> findByClient(Integer client_id) throws Exception {
        return appointmentRepository.findByClient(client_id);
    }

    @Override
    public List<Appointment> findByNutritionist(Integer nutritionist_id) throws Exception {
        return appointmentRepository.findByNutritionist(nutritionist_id);
    }


}
