package com.guowii.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulOrder80 {

    public static void main(String[] args) {
        SpringApplication.run(ConsulOrder80.class,args);
    }
}
