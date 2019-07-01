package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.entity.Pregdetils;
import com.medicalcare.medicalcareconsumer.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @RequestMapping(method = RequestMethod.POST,value = "addDrugs")
    boolean addDrugs(@RequestBody Map<String,Object> map){
        return doctorService.addDrugs(map);
    }

    @RequestMapping(method = RequestMethod.GET,value = "getAllPregdetils")
    List<Pregdetils> getAllPregdetils(@RequestParam("rid")String rid){
        return doctorService.getAllPregdetils(rid);
    }

}
