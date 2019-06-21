package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Information;
import com.medicalcare.medicalcareconsumer.service.RegisterInfoService;
import com.medicalcare.medicalcareconsumer.service.ResignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ResignController {
    @Autowired
    private ResignService resignService;
    @RequestMapping(method = RequestMethod.GET,value = "/getAllInformation")
    public List<Information> getAllInformation(){
        return resignService.getAllInformation();
    }
}
