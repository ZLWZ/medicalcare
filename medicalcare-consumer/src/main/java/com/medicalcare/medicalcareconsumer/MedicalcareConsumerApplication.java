package com.medicalcare.medicalcareconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.medicalcare")
@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MedicalcareConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalcareConsumerApplication.class, args);
    }

}
