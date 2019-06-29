package com.medicalcare.medicalcareprovide.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.medicalcare.entity.Drugs;
import com.medicalcare.entity.Pregdetils;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pharmacy")
@RequiresPermissions("pharmacy")
public class PrescriptController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @RequestMapping(method = RequestMethod.POST,value = "checkNum")
    String checkNum(@RequestBody Map<String,Object> map){
        String checknum = JSONArray.toJSONString(map.get("checknum"));
        List<Pregdetils> pregdetils = JSON.parseArray(checknum, Pregdetils.class);
        for(Pregdetils p :pregdetils){
            Drugs drugs = drugsServiceImpl.getDrugsByid(p.getDid());
            if(drugs.getKstate()==2) return drugs.getDname()+ "已禁售！！";
            if(p.getNumber()>drugs.getNum()) return drugs.getDname()+"只有"+drugs.getNum()+"个,重新选择数量！！";
        }
        return "";
    }
}
