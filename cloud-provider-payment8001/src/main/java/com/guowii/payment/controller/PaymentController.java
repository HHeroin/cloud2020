package com.guowii.payment.controller;

import com.guowii.common.entity.CommonResult;
import com.guowii.common.entity.Payment;
import com.guowii.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("/payment")
@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

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

    @GetMapping("/discovery")
    public Object getDiscoveryClient() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            log.info("instances--{} :{},url",service,instances);
            for (ServiceInstance instance : instances) {
                log.info("uri:{}",instance.getUri());
            }
        }
        return services;
    }

    @GetMapping("/timeOut")
    public String timeOut() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
