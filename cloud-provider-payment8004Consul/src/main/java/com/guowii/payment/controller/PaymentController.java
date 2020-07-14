package com.guowii.payment.controller;

import cn.hutool.core.lang.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @RequestMapping("/consul")
    public String getConsul() {
        return UUID.fastUUID().toString();
    }
}
