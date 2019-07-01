package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Drugs;
import com.medicalcare.entity.Pregdetils;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "MEDICALCARE-PROVIDE/doctor")
public interface DoctorService {
    @RequestMapping(method = RequestMethod.GET,value = "getAllDrugs")
    List<Drugs> getAllDrugs(@RequestParam("dname")String dname);

    @RequestMapping(method = RequestMethod.POST,value = "addDrugs")
    boolean addDrugs(@RequestBody Map<String,Object> map);

    @RequestMapping(method = RequestMethod.GET,value = "getAllPregdetils")
    List<Pregdetils> getAllPregdetils(@RequestParam("rid")String rid);

}
