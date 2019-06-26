package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.medicalcareprovide.service.PregdetilsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("doctor")
@RequiresPermissions(value = "/doctor")
public class DoctorController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @Autowired
    private PregdetilsService pregdetilsServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllDrugs")
    public List<Drugs> getAllDrugs(@RequestParam("dname")String dname){
        return drugsServiceImpl.getAllDrugs(dname);
    }
    @RequestMapping(method = RequestMethod.POST,value = "addDrugs")
    public boolean addDrugs(@RequestBody Map map){
        List<String> drugs = (List<String>)map.get("drugs");
        String rid = (String)map.get("rid");
        System.out.println(drugs.size());
        System.out.println(drugs);
        for(String dd :drugs){
            System.out.println(dd);
        }
        /*List<String> drugs = (List<String>)map.get("drugs");
        Pregdetils pregdetils = new Pregdetils();
        for(String dd :drugs){
            System.out.println(dd);
        }
        String rid = (String) map.get("rid");
        System.out.println(rid);*/
        return true;
    }
}
