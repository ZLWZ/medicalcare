package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.service.StorehouseService;
import com.medicalcare.util.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/Storehouse")
public class StorehouseController {

    @Resource
    private StorehouseService storehouseService;

    @PostMapping(value = "/selAllPageKcdrugs")
    public Result selAllPageKcdrugs(@RequestBody(required = false) Map<String,Object> map){
        System.out.println(map);
        return storehouseService.selAllPageKcdrugs(map);
    }


    @GetMapping(value = "/selList")
    public Result selList(){
        return storehouseService.selList();
    }
}
