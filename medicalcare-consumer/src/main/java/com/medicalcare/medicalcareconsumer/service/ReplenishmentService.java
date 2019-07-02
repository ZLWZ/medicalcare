package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Drugs;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE/pharmacy")
public interface ReplenishmentService {
    @RequestMapping(method = RequestMethod.GET,value = "getAllInfo")
    public Result getAllInfo();

    @RequestMapping(method = RequestMethod.GET,value = "getDrugDefi")
    public List<Drugs> getDrugDefi();

    @RequestMapping(method = RequestMethod.POST,value = "insertApply")
    public boolean insertApply(@RequestBody Map<String,Object> map);
}
