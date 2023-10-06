package com.example.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringBootDemoApplication::main).with(TestSpringBootDemoApplication.class).run(args);
    }

}
