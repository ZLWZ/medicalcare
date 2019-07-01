package com.medicalcare.service;

import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value="MEDICALCARE-PROVIDE/Storehouse")
public interface StorehouseService {

    @PostMapping(value = "/selAllPageKcdrugs")
    Result selAllPageKcdrugs(@RequestBody(required = false) Map<String,Object> map);


    @GetMapping(value = "/selList")
    Result selList();


    @PostMapping(value = "/addKcdrugs")
    Result addKcdrugs(@RequestBody(required = false) Map<String,Object> map);
}
