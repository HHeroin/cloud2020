package com.guowii.payment.controller;

import com.guowii.common.entity.CommonResult;
import com.guowii.common.entity.Payment;
import com.guowii.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RequestMapping("/payment")
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentService paymentService;

    @GetMapping("get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable String id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult<Payment>(CommonResult.SUCC_CODE, "请求成功" + serverPort, payment);
        } else {
            return new CommonResult<Payment>(CommonResult.FAIL_CODE, "未查找到数据" + serverPort);
        }
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        int i = paymentService.create(payment);
        if (i > 0) {
            return new CommonResult<>(CommonResult.SUCC_CODE, "插入成功" + serverPort, i);

        } else {
            return new CommonResult<>(CommonResult.SUCC_CODE, "插入失败" + serverPort, i);
        }
    }

    @GetMapping("/timeOut")
    public String timeOut() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
