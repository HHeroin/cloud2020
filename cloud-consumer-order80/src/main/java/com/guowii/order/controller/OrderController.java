package com.guowii.order.controller;

import com.guowii.common.entity.CommonResult;
import com.guowii.common.entity.Payment;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/consumer")
public class OrderController {

    private final static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private RestTemplate restTemplate;

    //private static final String PAYMENT_URL = "http://localhost:8001";
    private static final String PAYMENT_URL = "http://cloud-payment-service";


    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") String id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/lb")
    public CommonResult<Payment> getLbPayment() {
        String serviceId = "cloud-payment-service";
        // 访问次数每次+1，actomicInteger保证线程安全
        int visitCount = atomicInteger.getAndIncrement();
        // 获取所有服务实例
        List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceId);
        // 轮询算法 本次访问服务 = 访问次数 % 服务数
        int index = visitCount % instanceList.size();
        ServiceInstance serviceInstance = instanceList.get(index);
        URI uri = serviceInstance.getUri();
        // 通过RestTemplate根据uri调用服务
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(uri + "/payment/get/1", CommonResult.class);
        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return new CommonResult<>();
        }
    }
}
