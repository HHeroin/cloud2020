package com.guowii.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    private static final String PaymentService = "http://cloud-payment-service";

    @GetMapping("/payment/zk")
    public String get() {
        String result =  restTemplate.getForObject(PaymentService + "/payment/zk",String.class);
        return result;
    }
}
