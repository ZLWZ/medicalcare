package com.medicalcare.medicalcareserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MedicalcareServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalcareServerApplication.class, args);
    }

}
