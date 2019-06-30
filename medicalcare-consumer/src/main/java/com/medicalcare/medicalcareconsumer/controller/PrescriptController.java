package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Procdetils;
import com.medicalcare.medicalcareconsumer.service.PrescriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class PrescriptController {
    @Autowired
    PrescriptService prescriptService;
    @RequestMapping(method = RequestMethod.POST,value = "checkNum")
    String checkNum(@RequestBody Map<String,Object> map){
        return prescriptService.checkNum(map);
    }

    @RequestMapping(method = RequestMethod.POST,value = "addProcdetils")
    boolean addProcdetils(@RequestBody Map<String,Object> map){return prescriptService.addProcdetils(map); }

    @RequestMapping(method = RequestMethod.GET,value = "selProcdetils")
    List<Procdetils> selProcdetils(@RequestParam("rid") String rid){return prescriptService.selProcdetils(rid); }

}
