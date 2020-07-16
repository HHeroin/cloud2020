package com.guowii.custom;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRule {

    // 自定义负载均衡路由规则
    @Bean
    public IRule getRule() {
        return new RandomRule();
    }
}
