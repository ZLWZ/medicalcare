package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.medicalcareconsumer.service.ShouYeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class ShouYeController {
    @Autowired
    private ShouYeService shouYeService;
    @RequestMapping(method = RequestMethod.GET,value = "getInfo")
    public Map<String,Object> getInfo(){
        return shouYeService.getInfo();
    }

    @RequestMapping(method = RequestMethod.POST,value = "getReg")
    public Map<String,Object> getReg(@RequestBody Map<String,Object> map){
        return shouYeService.getReg(map);
    }
}
