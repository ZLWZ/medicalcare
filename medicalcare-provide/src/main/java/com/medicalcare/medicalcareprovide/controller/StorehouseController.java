package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.medicalcareprovide.service.CompanyService;
import com.medicalcare.medicalcareprovide.service.DosageService;
import com.medicalcare.medicalcareprovide.service.KcdrugsService;
import com.medicalcare.medicalcareprovide.service.SpecifiService;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 药库管理
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/Storehouse")
@RequiresPermissions("storehouse")
public class StorehouseController {

    @Resource
    private KcdrugsService kcdrugsServiceImpl;

    @Resource
    private DosageService dosageServiceImpl;

    @Resource
    private CompanyService companyServiceImpl;

    @Resource
    private SpecifiService specifiServiceImpl;

    @PostMapping(value = "/selAllPageKcdrugs")
    @RequiresPermissions("storehouse")
    public Result selAllPageKcdrugs(@RequestBody(required = false) Map<String,Object> map){
        int page = Integer.parseInt(map.get("page").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        map.put("page",(page - 1) * pageSize);
        List<String> mkdateList = (List<String>) map.get("mkdate");
        List<String> leavedateList = (List<String>)map.get("leavedate");
        List<String> joindateList = (List<String>) map.get("joindate");
        if(mkdateList != null && !mkdateList.isEmpty()){
            map.put("mkStartDate",mkdateList.get(0));
            map.put("mkStopDate",mkdateList.get(1));
        }
        if(leavedateList != null && !leavedateList.isEmpty()){
            map.put("leaveStartDate",leavedateList.get(0));
            map.put("leaveStopDate",leavedateList.get(1));
        }
        if(joindateList != null && !joindateList.isEmpty()){
            map.put("joinStartDate",joindateList.get(0));
            map.put("joinStopDate",joindateList.get(1));
        }
        PageResult<Kcdrugs> pageResult = new PageResult<Kcdrugs>();
        pageResult.setTotal(kcdrugsServiceImpl.selKcdrugsCount(map));
        pageResult.setRows(kcdrugsServiceImpl.selAllPageKcdrugs(map));
        return new Result(ResultCode.SUCCESS,pageResult);
    }


    @GetMapping(value = "/selList")
    @RequiresPermissions("storehouse")
    public Result selList(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dosage",dosageServiceImpl.selAllDosage());
        map.put("company",companyServiceImpl.selAllCompany());
        map.put("specifi",specifiServiceImpl.selAllSpecifi());
        return new Result(ResultCode.SUCCESS,map);
    }

}
