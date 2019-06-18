package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Shift;
import com.medicalcare.medicalcareconsumer.service.DutyService;
import com.medicalcare.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class DutyController {
    @Autowired
    private DutyService dutyService;
    @RequestMapping(method = RequestMethod.GET,value = "getAllDuty")
    public List<Shift> getAllDuty(@RequestParam("sid") String sid,@RequestParam("uname") String uname, @RequestParam("sdate") String sdate, @RequestParam("did") String did){
        return  dutyService.getAllDuty(sid,uname, sdate, did);
    }
    @RequestMapping(method = RequestMethod.GET, value="getAllDutyInfo")
    public Result getAllDutyInfo(@RequestParam("uid") String uid){
        return dutyService.getAllDutyInfo(uid);
    };
    @RequestMapping(value = "updateShift",method = RequestMethod.POST)
    public boolean updateShift(@RequestBody Shift shift){
        return dutyService.updateShift(shift);
    }
    @RequestMapping(value = "insertShift",method = RequestMethod.POST)
    public boolean insertShift(@RequestBody Shift shift){
        return dutyService.insertShift(shift);
    }
}
