package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctor")
@RequiresPermissions(value = "/doctor")
public class DoctorController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllDrugs")
    public List<Drugs> getAllDrugs(@RequestParam("dname")String dname){
        return drugsServiceImpl.getAllDrugs(dname);
    }
}
