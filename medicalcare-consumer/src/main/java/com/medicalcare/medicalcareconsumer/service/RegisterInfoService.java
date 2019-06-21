package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Register;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE/cashier")
public interface RegisterInfoService {
    @RequestMapping(method = RequestMethod.GET,value = "getAllRegister")
    List<Register> getAllRegister(@RequestParam("rid") String rid, @RequestParam("rname") String rname);

    @RequestMapping(value = "getAllInfo",method = RequestMethod.GET)
    Result getAllInfo(@RequestParam("did") Long did);

    @RequestMapping(value = "getRegister",method = RequestMethod.GET)
    public Register getRegister(@RequestParam("rid") String rid);

    @RequestMapping(value = "updateRegister",method = RequestMethod.POST)
    public boolean updateRegister(@RequestBody Register register);

    @RequestMapping(value = "deleteRegister",method = RequestMethod.GET)
    boolean deleteRegister(@RequestParam("rid") String rid);
}
