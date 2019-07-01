package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Drugs;
import com.medicalcare.util.PageResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE/pharmacy")
public interface InquiriesService {
    @RequestMapping(method = RequestMethod.POST,value = "getAllDrugs")
    public PageResult<Drugs> getAllDrugs(@RequestBody Map<String,String> map);

    @RequestMapping(method = RequestMethod.GET,value = "updateDrugState")
    public boolean updateDrugState(@RequestParam("id")Long id,@RequestParam("kstate")Long kstate);
}
