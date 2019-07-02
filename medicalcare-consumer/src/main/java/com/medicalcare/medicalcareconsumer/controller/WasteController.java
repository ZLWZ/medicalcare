package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.service.WasteService;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/waste")
public class WasteController {

    @Resource
    private WasteService wasteService;


    @PostMapping("/selWaste")
    public Result selStock(@RequestBody Map<Object,String> map){
        return wasteService.selStock(map);
    }




}
