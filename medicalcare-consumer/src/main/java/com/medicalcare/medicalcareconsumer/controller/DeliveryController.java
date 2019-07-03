package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Apply;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.medicalcareconsumer.service.DeliveryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/medicine")
@CrossOrigin
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @RequestMapping(method = RequestMethod.GET,value = "getAllApply")
    public List<Apply> getAllApply(@RequestParam("begin")String begin,@RequestParam("end")String end){
        return deliveryService.getAllApply(begin,end);
    }
    @RequestMapping(method = RequestMethod.POST,value = "outYao")
    public boolean outYao(@RequestBody Kcdrugs kcdrugs){
        return deliveryService.outYao(kcdrugs);
    }

    @RequestMapping(method = RequestMethod.POST,value = "chuYao")
    public boolean chuYao(@RequestBody Kcdrugs kcdrugs){
        return deliveryService.chuYao(kcdrugs);
    }

}
