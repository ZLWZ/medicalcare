package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Dosage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE")
public interface DosageService {


    @RequestMapping(method = RequestMethod.GET, value="query")
    public List<Dosage> query();

    @RequestMapping("/update")
    public String update();
}
