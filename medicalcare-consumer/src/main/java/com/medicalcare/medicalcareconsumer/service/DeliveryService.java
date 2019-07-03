package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Apply;
import com.medicalcare.entity.Kcdrugs;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE/medicine")
public interface DeliveryService {
    @RequestMapping(method = RequestMethod.GET,value = "getAllApply")
    public List<Apply> getAllApply(@RequestParam("begin")String begin, @RequestParam("end")String end);

    @RequestMapping(method = RequestMethod.POST,value = "outYao")
    public boolean outYao(@RequestBody Kcdrugs kcdrugs);

    @RequestMapping(method = RequestMethod.POST,value = "chuYao")
    public boolean chuYao(@RequestBody Kcdrugs kcdrugs);
}
