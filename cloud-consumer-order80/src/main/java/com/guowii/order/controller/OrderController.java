package com.guowii.order.controller;

import com.guowii.common.entity.CommonResult;
import com.guowii.common.entity.Payment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer")
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    private static final String PAYMENT_URL = "http://localhost:8001/payment";

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") String id) {
        return restTemplate.getForObject(PAYMENT_URL + "/get/" + id, CommonResult.class);
    }

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/create",payment,CommonResult.class);
    }
}