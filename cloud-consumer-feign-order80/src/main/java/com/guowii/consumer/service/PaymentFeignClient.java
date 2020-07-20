package com.guowii.consumer.service;

import com.guowii.common.entity.CommonResult;
import com.guowii.common.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient("cloud-payment-service")
@Service
@RequestMapping("/payment")
public interface PaymentFeignClient {

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") String id);

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody Payment payment);
}
