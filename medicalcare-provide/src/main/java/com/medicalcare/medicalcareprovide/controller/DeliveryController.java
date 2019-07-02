package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Apply;
import com.medicalcare.medicalcareprovide.service.ApplyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("medicine")
@RequiresPermissions("medicine")
public class DeliveryController {
    @Autowired
    private ApplyService ApplyServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllApply")
    public List<Apply> getAllApply(){
        return ApplyServiceImpl.getAllApply();
    }
}
