package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Apply;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.medicalcareprovide.service.ApplyService;
import com.medicalcare.medicalcareprovide.service.KcdrugsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("medicine")
@RequiresPermissions("medicine")
public class DeliveryController {
    @Autowired
    private ApplyService applyServiceImpl;
    @Autowired
    private KcdrugsService kcdrugsServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllApply")
    public List<Apply> getAllApply(@RequestParam("begin")String begin, @RequestParam("end")String end){
        return applyServiceImpl.getAllApply(begin,end);
    }
    @RequestMapping(method = RequestMethod.POST,value = "outYao")
    public boolean outYao(@RequestBody Kcdrugs kcdrugs){
        return kcdrugsServiceImpl.outYao(kcdrugs);
    }
    @RequestMapping(method = RequestMethod.POST,value = "chuYao")
    public boolean chuYao(@RequestBody Kcdrugs kcdrugs){
        return kcdrugsServiceImpl.chuYao(kcdrugs);
    }
}
