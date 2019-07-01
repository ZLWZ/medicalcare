package com.medicalcare.service;

import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value="MEDICALCARE-PROVIDE/waste")
public interface WasteService {

    @PostMapping("/selWaste")
    Result selStock(@RequestBody Map<Object,String> map);
}
