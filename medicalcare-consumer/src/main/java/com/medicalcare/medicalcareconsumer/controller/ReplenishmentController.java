package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareconsumer.service.ReplenishmentService;
import com.medicalcare.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class ReplenishmentController {
    @Autowired
    private ReplenishmentService replenishmentService;
    @RequestMapping(method = RequestMethod.GET,value = "getAllInfo")
    public Result getAllInfo(){
        return replenishmentService.getAllInfo();
    }

    @RequestMapping(method = RequestMethod.GET,value = "getDrugDefi")
    public List<Drugs> getDrugDefi(){
        return replenishmentService.getDrugDefi();
    }

    @RequestMapping(method = RequestMethod.POST,value = "insertApply")
    public boolean insertApply(@RequestBody Map<String,Object> map){
        return  replenishmentService.insertApply(map);
    }

}
