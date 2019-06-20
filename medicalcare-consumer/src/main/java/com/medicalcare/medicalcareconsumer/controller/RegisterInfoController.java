package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareconsumer.service.RegisterInfoService;
import com.medicalcare.util.Result;
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
    @RequestMapping(value = "getAllInfo",method = RequestMethod.GET)
    public Result getAllInfo(@RequestParam("did") Long did){
        return registerInfoService.getAllInfo(did);
    }
    @RequestMapping(value = "getRegister",method = RequestMethod.GET)
    public Register getRegister(@RequestParam("rid") String rid){
        return registerInfoService.getRegister(rid);
    }

    @RequestMapping(value = "updateRegister",method = RequestMethod.POST)
    public boolean updateRegister(@RequestBody Register register){
        return registerInfoService.updateRegister(register);
    }

    @RequestMapping(value = "deleteRegister",method = RequestMethod.GET)
    public boolean deleteRegister(@RequestParam("rid") String rid){
        return registerInfoService.deleteRegister(rid);
    }
}
