package com.guowii.consumer.controller;

import com.guowii.common.entity.CommonResult;
import com.guowii.common.entity.Payment;
import com.guowii.consumer.service.PaymentFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/consumer")
@RestController
public class OrderController {

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    @GetMapping("/feign/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable String id) {
        return paymentFeignClient.getPaymentById(id);
    }

    @PostMapping("/feign/payment/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        return paymentFeignClient.create(payment);
    }
}
