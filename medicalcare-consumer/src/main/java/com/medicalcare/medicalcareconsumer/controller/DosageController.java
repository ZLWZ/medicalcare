package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Dosage;
import com.medicalcare.medicalcareconsumer.service.DosageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class DosageController {

    @Resource
    private DosageService dosageService;

    @RequestMapping(value = "query",method = RequestMethod.GET)
    public List<Dosage> query(){
        System.out.println(111);
        return dosageService.query();
    }

    @RequestMapping("/update")
    public String update(HttpServletRequest request){
        String authoriztion = request.getHeader("Authoriztion");
        System.out.println("update:"+authoriztion);
        return dosageService.update();
    }
}
