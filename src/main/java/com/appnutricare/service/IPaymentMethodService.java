package com.appnutricare.service;

import com.appnutricare.entities.PaymentMethod;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPaymentMethodService  extends CrudService<PaymentMethod>{
    public List<PaymentMethod> findAllByClient(Integer client_id) throws Exception;
}
