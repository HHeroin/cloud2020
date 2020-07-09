package com.guowii.payment.controller;

import com.guowii.common.entity.CommonResult;
import com.guowii.common.entity.Payment;
import com.guowii.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/payment")
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable String id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult<Payment>(CommonResult.SUCC_CODE, "请求成功", payment);
        } else {
            return new CommonResult<Payment>(CommonResult.FAIL_CODE, "未查找到数据");
        }
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        int i = paymentService.create(payment);
        if (i > 0) {
            return new CommonResult<>(CommonResult.SUCC_CODE, "插入成功", i);

        } else {
            return new CommonResult<>(CommonResult.SUCC_CODE, "插入失败", i);
        }
    }
}
