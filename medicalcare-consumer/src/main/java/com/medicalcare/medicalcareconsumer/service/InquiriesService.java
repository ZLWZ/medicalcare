package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Drugs;
import com.medicalcare.util.PageResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE/pharmacy")
public interface InquiriesService {
    @RequestMapping(method = RequestMethod.POST,value = "getAllDrugs")
    public PageResult<Drugs> getAllDrugs(@RequestParam("current") Integer current, @RequestParam("size") Integer size,@RequestBody Drugs drugs);
}
