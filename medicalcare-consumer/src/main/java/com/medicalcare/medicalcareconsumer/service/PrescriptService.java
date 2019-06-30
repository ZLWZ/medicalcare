package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Procdetils;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE/pharmacy")
public interface PrescriptService {

    @RequestMapping(method = RequestMethod.POST,value = "checkNum")
    String checkNum(@RequestBody Map<String,Object> map);

    @RequestMapping(method = RequestMethod.POST,value = "addProcdetils")
    boolean addProcdetils(@RequestBody Map<String, Object> map);

    @RequestMapping(method = RequestMethod.GET,value = "selProcdetils")
    List<Procdetils> selProcdetils(@RequestParam("rid") String rid);
}
