package com.guowii.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class ConsulOrderController {
    @Resource
    private RestTemplate restTemplate;

    private static final String PAYMENT_SERVICE = "http://cloud-payment-service";

    @GetMapping("/payment/consul")
    public String getFromConsul() {
        return restTemplate.getForObject(PAYMENT_SERVICE + "/payment/consul",String.class);
    }
}
