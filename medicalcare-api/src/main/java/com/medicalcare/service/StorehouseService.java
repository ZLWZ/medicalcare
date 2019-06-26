package com.medicalcare.service;

import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value="MEDICALCARE-PROVIDE/Storehouse")
public interface StorehouseService {

    @PostMapping(value = "/selAllPageKcdrugs")
    public Result selAllPageKcdrugs(Map map);


    @GetMapping(value = "/selList")
    public Result selList();
}
