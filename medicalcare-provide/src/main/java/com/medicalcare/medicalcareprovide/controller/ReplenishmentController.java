package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Apply;
import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.service.ApplyService;
import com.medicalcare.medicalcareprovide.service.DepartmentService;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.medicalcareprovide.service.SpecifiService;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pharmacy")
@RequiresPermissions("pharmacy")
public class ReplenishmentController {
    @Autowired
    private DepartmentService departmentServiceImpl;
    @Autowired
    private SpecifiService specifiServiceImpl;
    @Autowired
    private DrugsService drugsServiceImpl;
    @Autowired
    private ApplyService applyServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllInfo")
    public Result getAllInfo(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("department",departmentServiceImpl.getAllDepartment());
        map.put("specifi",specifiServiceImpl.selAllSpecifi());
        return new Result(ResultCode.SUCCESS,map);
    }
    @RequestMapping(method = RequestMethod.GET,value = "getDrugDefi")
    public List<Drugs> getDrugDefi(){
        List<Drugs> drugDefi = drugsServiceImpl.getDrugDefi();
        for(Drugs drugs:drugDefi){
            drugs.setSpecifi(specifiServiceImpl.selBysid(drugs.getSid()));
        }
        return drugDefi;
    }

    @RequestMapping(method = RequestMethod.POST,value = "insertApply")
    public boolean insertApply(@RequestBody Map<String,Object> map){
        return  applyServiceImpl.insertApply(map);
    }
}
