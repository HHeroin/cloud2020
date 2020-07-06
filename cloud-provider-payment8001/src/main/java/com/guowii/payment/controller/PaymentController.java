package com.guowii.payment.controller;

import com.guowii.payment.entity.CommonResult;
import com.guowii.payment.entity.Payment;
import com.guowii.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(name = "id") String id) {
        Payment payment = paymentService.getPaymentById(id);
        return new CommonResult<>(200,"请求成功",payment);
    }
}
