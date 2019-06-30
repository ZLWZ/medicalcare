package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.util.PageResult;
import javafx.geometry.Pos;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pharmacy")
@RequiresPermissions("pharmacy")
public class InquiriesController {
    @Autowired
    private DrugsService drugsServiceImpl;
    @RequestMapping(method = RequestMethod.POST,value = "getAllDrugs")
    public PageResult<Drugs> getAllDrugs(@RequestParam("current") Integer current, @RequestParam("size") Integer size,@RequestBody Drugs drugs){
        return drugsServiceImpl.getAllDrugs(drugs,current,size);
    }

}
