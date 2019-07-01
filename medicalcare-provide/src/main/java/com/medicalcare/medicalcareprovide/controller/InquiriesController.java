package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.util.PageResult;
import javafx.geometry.Pos;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pharmacy")
@RequiresPermissions("pharmacy")
public class InquiriesController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @RequestMapping(method = RequestMethod.POST,value = "getAllDrugs")
    public PageResult<Drugs> getAllDrugs(@RequestBody Map<String,String> map){
        return drugsServiceImpl.getAllDrugs(map);
    }
    @RequestMapping(method = RequestMethod.GET,value = "updateDrugState")
    public boolean updateDrugState(@RequestParam("id")Long id,@RequestParam("kstate")Long kstate){
        return drugsServiceImpl.updateDrugState(id,kstate);
    }
}
