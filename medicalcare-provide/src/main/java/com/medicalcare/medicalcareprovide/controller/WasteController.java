package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Stock;
import com.medicalcare.medicalcareprovide.service.StockService;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/waste")
@CrossOrigin
public class WasteController {

    @Resource
    private StockService stockServiceImpl;

    @RequiresPermissions("waste")
    @PostMapping("/selWaste")
    public Result selStock(@RequestBody Map<String,Object> map){
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
        PageResult<Stock> pageResult = new PageResult<Stock>();
        pageResult.setTotal(stockServiceImpl.selCount(map));
        pageResult.setRows(stockServiceImpl.selStock(map));
        return new Result(ResultCode.SUCCESS,pageResult);
    }




}
