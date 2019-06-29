package com.medicalcare.medicalcareprovide.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.medicalcare.entity.Drugs;
import com.medicalcare.entity.Pregdetils;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.medicalcareprovide.service.PregdetilsService;
import com.medicalcare.medicalcareprovide.service.RegisterService;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("doctor")
@RequiresPermissions("doctor")
public class DoctorController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @Autowired
    private PregdetilsService pregdetilsServiceImpl;
    @Autowired
    private RegisterService registerServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllDrugs")
    public List<Drugs> getAllDrugs(@RequestParam("dname")String dname){
        return drugsServiceImpl.getAllDrugs(dname);
    }
    @RequestMapping(method = RequestMethod.POST,value = "addDrugs")
    public boolean addDrugs(@RequestBody Map<String,Object> map){
        String drugs = JSONArray.toJSONString(map.get("drugs"));
        List<Pregdetils> objects = JSON.parseArray(drugs,Pregdetils.class);
        String rid = (String) map.get("rid");
        boolean flag = false;
        double rprice = 0;
        for (Pregdetils l : objects){
            rprice += l.getZmoney();
            l.setRid(rid);
            flag = pregdetilsServiceImpl.addPregdetils(l);
        }
        if(flag){
            return registerServiceImpl.updateRstatic(rid,2,rprice);
        }else{
            return false;
        }
    }
    @RequestMapping(method = RequestMethod.GET,value = "getAllPregdetils")
    List<Pregdetils> getAllPregdetils(@RequestParam("rid")String rid){
        return pregdetilsServiceImpl.getAllPregdetils(rid);
    }
}
