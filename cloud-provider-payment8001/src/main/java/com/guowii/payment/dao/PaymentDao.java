package com.guowii.payment.dao;

import com.guowii.payment.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentDao {

    Payment getPaymentById(String id);
}
