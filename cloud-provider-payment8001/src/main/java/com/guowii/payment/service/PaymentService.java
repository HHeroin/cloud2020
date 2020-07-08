package com.guowii.payment.service;

import com.guowii.payment.dao.PaymentDao;
import com.guowii.payment.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    public Payment getPaymentById(String id) {
        return paymentDao.getPaymentById(id);
    }

    public int create(Payment payment) {
        int i = paymentDao.create(payment);
        log.info("payment:{}",payment);
        return i;
    }
}
