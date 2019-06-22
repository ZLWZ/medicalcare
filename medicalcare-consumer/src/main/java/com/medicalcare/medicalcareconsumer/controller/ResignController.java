package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Information;
import com.medicalcare.medicalcareconsumer.service.RegisterInfoService;
import com.medicalcare.medicalcareconsumer.service.ResignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ResignController {
    @Autowired
    private ResignService resignService;
    @RequestMapping(method = RequestMethod.GET,value = "/getAllInformation")
    public List<Information> getAllInformation(@RequestParam("begin")String begin,@RequestParam("end")String end){
        return resignService.getAllInformation(begin,end);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/deleteInformation")
    public boolean deleteInformation(@RequestParam("uid") String uid){
        return resignService.deleteInformation(uid);
    }

}
