package com.medicalcare.medicalcareprovide.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.entity.Stock;
import com.medicalcare.medicalcareprovide.service.*;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 药库管理
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/Storehouse")
public class StorehouseController {

    @Resource
    private KcdrugsService kcdrugsServiceImpl;

    @Resource
    private DosageService dosageServiceImpl;

    @Resource
    private CompanyService companyServiceImpl;

    @Resource
    private SpecifiService specifiServiceImpl;


    @Resource
    private StockService stockServiceImpl;

    @PostMapping(value = "/selAllPageKcdrugs")
    @RequiresPermissions("storehouse")
    public Result selAllPageKcdrugs(@RequestBody(required = false) Map<String,Object> map){
        kcdrugsServiceImpl.delKcdrugs();
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
        pageResult.setRows(kcdrugsServiceImpl.selAllPageKcdrugs(map));
        pageResult.setTotal(kcdrugsServiceImpl.selKcdrugsCount(map));
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

    @RequiresPermissions("storehouse")
    @PostMapping(value = "/addKcdrugs")
    public Result addKcdrugs(@RequestBody(required = false) Map<String,Object> map){
        String kcdrugss = JSONArray.toJSONString(map.get("kcdrugss"));
        String uid = map.get("uid").toString();
        List<Kcdrugs> kcdrugs = JSON.parseArray(kcdrugss, Kcdrugs.class);
        List<Stock> stocks = new ArrayList<Stock>();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        for (Kcdrugs k : kcdrugs){
            k.setJoindate(timestamp);
            k.setUid(uid);
            BigDecimal bigDecimal = new BigDecimal(Double.toString(k.getNum()));
            BigDecimal bigDecimal1 = new BigDecimal(Double.toString(k.getMoney()));
            stockServiceImpl.addStock(new Stock().setSname(k.getDname()).setJoindate(k.getJoindate()).setLeavedate(k.getLeavedate()).setMkdate(k.getMkdate())
                    .setStype(k.getKtype()).setNum(k.getNum()).setCid(k.getCid()).setSid(k.getSid()).setDid(k.getDid()).setDmoney(k.getMoney())
                    .setZmoney(bigDecimal.multiply(bigDecimal1).doubleValue()).setUid(uid));
        }
        kcdrugsServiceImpl.addKcdrugs(kcdrugs);
        return new Result(ResultCode.SUCCESS);
    }

}
