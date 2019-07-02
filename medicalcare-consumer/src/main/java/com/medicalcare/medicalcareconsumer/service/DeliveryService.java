package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Apply;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE/medicine")
public interface DeliveryService {
    @RequestMapping(method = RequestMethod.GET,value = "getAllApply")
    public List<Apply> getAllApply();
}
