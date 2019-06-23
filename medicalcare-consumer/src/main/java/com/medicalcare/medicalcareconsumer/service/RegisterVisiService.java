package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Register;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("MEDICALCARE-PROVIDE/cashier")
public interface RegisterVisiService {
    @RequestMapping(value = "addRegister",method = RequestMethod.POST)
    boolean addRegister(@RequestBody Register register);
}
