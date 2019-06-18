package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Register;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE/cashier")
public interface RegisterInfoService {
    @RequestMapping(method = RequestMethod.GET,value = "getAllRegister")
    List<Register> getAllRegister(@RequestParam("rid") String rid, @RequestParam("rname") String rname);
}
