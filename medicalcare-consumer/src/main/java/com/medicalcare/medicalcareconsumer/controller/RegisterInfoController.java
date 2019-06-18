package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareconsumer.service.RegisterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashier")
@CrossOrigin
public class RegisterInfoController {
    @Autowired
    private RegisterInfoService registerInfoService;
    @RequestMapping(method = RequestMethod.GET,value = "getAllRegister")
    public List<Register> getAllRegister (@RequestParam("rid") String rid,@RequestParam("rname") String rname){
        return registerInfoService.getAllRegister(rid,rname);
    }
}
