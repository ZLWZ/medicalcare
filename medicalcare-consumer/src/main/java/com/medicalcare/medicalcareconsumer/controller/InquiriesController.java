package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareconsumer.service.InquiriesService;
import com.medicalcare.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class InquiriesController {
    @Autowired
    private InquiriesService inquiriesService;
    @RequestMapping(method = RequestMethod.POST,value = "getAllDrugs")
    public PageResult<Drugs> getAllDrugs(@RequestBody Map<String,String> map){
        return inquiriesService.getAllDrugs(map);
    };
    @RequestMapping(method = RequestMethod.GET,value = "updateDrugState")
    public boolean updateDrugState(@RequestParam("id")Long id,@RequestParam("kstate")Long kstate){
        return inquiriesService.updateDrugState(id,kstate);
    };

}
