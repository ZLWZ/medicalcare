package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Information;
import com.medicalcare.entity.User;
import com.medicalcare.medicalcareprovide.service.InformationService;
import com.medicalcare.medicalcareprovide.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("manager")
@RequiresPermissions(value = "/manager")
public class ResignController {
    @Autowired
    private InformationService informationServiceImpl;
    @Autowired
    private UserService userService;
    @RequestMapping(method = RequestMethod.GET,value = "/getAllInformation")
    public List<Information> getAllInformation(){
        List<Information> allInformation = informationServiceImpl.getAllInformation();
        for (Information info : allInformation){
            info.setUser(userService.getInfoUser(info.getUid()));
        }
        return allInformation;
    }
}
