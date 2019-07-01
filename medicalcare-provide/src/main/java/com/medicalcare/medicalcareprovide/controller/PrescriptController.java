package com.medicalcare.medicalcareprovide.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.medicalcare.entity.Drugs;
import com.medicalcare.entity.Pregdetils;
import com.medicalcare.entity.Procdetils;
import com.medicalcare.medicalcareprovide.mapper.RegisterMapper;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.medicalcareprovide.service.ProcdetilsService;
import com.medicalcare.medicalcareprovide.service.RegisterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pharmacy")
@RequiresPermissions("pharmacy")
public class PrescriptController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @Autowired
    private ProcdetilsService procdetilsServiceImpl;
    @Autowired
    private RegisterService registerServiceImpl;
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

    @RequestMapping(method = RequestMethod.POST,value = "addProcdetils")
    boolean addProcdetils(@RequestBody Map<String,Object> map){
        boolean flag = false;
        String checknum = JSONArray.toJSONString(map.get("checknum"));
        List<Procdetils> procdetils = JSON.parseArray(checknum, Procdetils.class);
        String rid = (String)map.get("rid");
        for(Procdetils p : procdetils){
            p.setRid(rid);
            flag = procdetilsServiceImpl.addProcdetils(p);
        }
        if(flag){
            return updateDrugs(procdetils,rid);
        }else{
            return flag;
        }

    }
    @RequestMapping(method = RequestMethod.GET,value = "selProcdetils")
    List<Procdetils> selProcdetils(@RequestParam("rid") String rid){
        return procdetilsServiceImpl.selProcdetils(rid);
    }

    private boolean updateDrugs(List<Procdetils> procdetils,String rid){
        double rprice = 0;
        for(Procdetils p :procdetils){
            Drugs drugs = drugsServiceImpl.getDrugsByid(p.getDid());
            drugs.setNum(drugs.getNum()-p.getNumber());
            rprice += p.getZmoney();
            drugsServiceImpl.updateDrugs(drugs);
        }
        return  registerServiceImpl.updatePstate(rid,rprice,1L);
    }

}
