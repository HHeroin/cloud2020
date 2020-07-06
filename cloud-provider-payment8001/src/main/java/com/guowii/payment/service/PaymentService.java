package com.guowii.payment.service;

import com.guowii.payment.dao.PaymentDao;
import com.guowii.payment.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    public Payment getPaymentById(String id) {
        return paymentDao.getPaymentById(id);
    }
}
