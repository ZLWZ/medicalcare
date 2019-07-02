package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Apply;
import com.medicalcare.medicalcareconsumer.service.DeliveryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/medicine")
@CrossOrigin
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @RequestMapping(method = RequestMethod.GET,value = "getAllApply")
    public List<Apply> getAllApply(){
        return deliveryService.getAllApply();
    }

}
