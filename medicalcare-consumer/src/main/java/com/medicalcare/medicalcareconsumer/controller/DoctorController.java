package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareconsumer.service.DoctorService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@CrossOrigin
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @RequestMapping(method = RequestMethod.GET,value = "getAllDrugs")
    List<Drugs> getAllDrugs(@RequestParam("dname")String dname){
        return doctorService.getAllDrugs(dname);
    }
}
