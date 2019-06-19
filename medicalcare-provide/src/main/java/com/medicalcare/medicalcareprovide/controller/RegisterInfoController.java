package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareprovide.mapper.RegisterMapper;
import com.medicalcare.medicalcareprovide.service.RegisterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cashier")
@RequiresPermissions(value = "/manager")
public class RegisterInfoController {
    @Autowired
    private RegisterService RegisterServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllRegister")
    public List<Register> getAllRegister(@RequestParam("rid") String rid,@RequestParam("rname") String rname){
        return RegisterServiceImpl.getAllRegister(rid,rname);
    }
}
