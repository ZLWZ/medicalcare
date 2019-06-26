package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Drugs;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "MEDICALCARE-PROVIDE/doctor")
public interface DoctorService {
    @RequestMapping(method = RequestMethod.GET,value = "getAllDrugs")
    List<Drugs> getAllDrugs(@RequestParam("dname")String dname);
}
