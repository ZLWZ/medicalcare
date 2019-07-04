package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class ShouYeController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @Autowired
    private KcdrugsService kcdrugsServiceImpl;
    @Autowired
    private RegisterService registerServiceImpl;
    @Autowired
    private RoleService roleServiceImpl;
    @Autowired
    private UserService userServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getInfo")
    public Map<String,Object> getInfo(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drugs",drugsServiceImpl.getTongJi());
        map.put("kcdrugs",kcdrugsServiceImpl.getTongJi());
        map.put("role",roleServiceImpl.getTongJi());
        map.put("user",userServiceImpl.getTongJi());
        return map;
    }

    @RequestMapping(method = RequestMethod.POST,value = "getReg")
    public Map<String,Object> getReg(@RequestBody Map<String,Object> map){
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("dataYu",registerServiceImpl.getReg(map,0));
        m.put("dataYi",registerServiceImpl.getReg(map,1));
        return m;
    }

}
