package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.medicalcareconsumer.service.PrescriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class PrescriptController {
    @Autowired
    PrescriptService prescriptService;
    @RequestMapping(method = RequestMethod.POST,value = "checkNum")
    String checkNum(@RequestBody Map<String,Object> map){
        return prescriptService.checkNum(map);
    }

}
