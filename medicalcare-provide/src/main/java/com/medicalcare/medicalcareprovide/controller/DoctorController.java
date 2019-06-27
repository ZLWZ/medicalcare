package com.medicalcare.medicalcareprovide.controller;

import com.alibaba.fastjson.JSON;
import com.medicalcare.entity.Drugs;
import com.medicalcare.entity.Pregdetils;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.medicalcareprovide.service.PregdetilsService;
import com.medicalcare.medicalcareprovide.service.RegisterService;
import net.sf.ehcache.transaction.xa.commands.StorePutCommand;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("doctor")
@RequiresPermissions(value = "/doctor")
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
    public boolean addDrugs(@RequestBody Map map){
        ArrayList drugs = (ArrayList)map.get("drugs");
        String rid = (String) map.get("rid");
        boolean flag = false;
        double rprice = 0;
        for(Object drug:drugs){
            List<String> list = new ArrayList<String>();
            String s = drug.toString();
            s = s.substring(0,s.length()-1);
            s = s.substring(1,s.length());
            String[] split = s.split(",");
            for(int i=0;i<split.length;i++){
                list.add((split[i].split("="))[1]);
            }
            Pregdetils pregdetils = new Pregdetils();
            pregdetils.setRid(rid);
            pregdetils.setRdname(list.get(1));
            pregdetils.setDmoney(Double.parseDouble(list.get(2)));
            pregdetils.setNumber(Long.parseLong(list.get(4)));
            pregdetils.setZmoney(Double.parseDouble(list.get(5)));
            rprice += pregdetils.getZmoney();
            flag = pregdetilsServiceImpl.addPregdetils(pregdetils);
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
