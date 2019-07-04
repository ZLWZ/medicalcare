package com.medicalcare.medicalcareconsumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE")
public interface ShouYeService {
    @RequestMapping(method = RequestMethod.GET,value = "getInfo")
    Map<String, Object> getInfo();

    @RequestMapping(method = RequestMethod.POST,value = "getReg")
    public Map<String,Object> getReg(@RequestBody Map<String,Object> map);
}
