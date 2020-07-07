package com.guowii.payment.controller;

import com.guowii.payment.entity.CommonResult;
import com.guowii.payment.entity.Payment;
import com.guowii.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/payment")
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/{xxx}")
    public CommonResult<Payment> getPaymentById(@PathVariable String xxx) {
        Payment payment = paymentService.getPaymentById(xxx);
        return new CommonResult<Payment>(200,"请求成功",payment);
    }
}
