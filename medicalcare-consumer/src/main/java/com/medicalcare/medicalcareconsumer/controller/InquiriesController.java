package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareconsumer.service.InquiriesService;
import com.medicalcare.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class InquiriesController {
    @Autowired
    private InquiriesService inquiriesService;
    @RequestMapping(method = RequestMethod.POST,value = "getAllDrugs")
    public PageResult<Drugs> getAllDrugs(@RequestBody Drugs drugs, @RequestParam("current") Integer current, @RequestParam("size") Integer size){
        return inquiriesService.getAllDrugs(current,size,drugs);
    };
}
