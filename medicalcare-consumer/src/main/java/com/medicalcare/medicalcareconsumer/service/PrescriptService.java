package com.medicalcare.medicalcareconsumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE/pharmacy")
public interface PrescriptService {

    @RequestMapping(method = RequestMethod.POST,value = "checkNum")
    String checkNum(@RequestBody Map<String,Object> map);
}
