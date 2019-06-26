package com.medicalcare.medicalcareconsumer.controller;


import com.medicalcare.service.APIService;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class APIController {

    @Resource
    private APIService apiService;

    @GetMapping("/delivery/api")
    @RequiresPermissions("delivery")
    public Result delivery(){
        return apiService.delivery();
    }
    @GetMapping("/storehouse/api")
    @RequiresPermissions("storehouse")
    public Result storehouse(){
        return apiService.storehouse();
    }

    @GetMapping("/waste/api")
    @RequiresPermissions("waste")
    public Result waste(){
        return apiService.waste();
    }

    @GetMapping("/replenishment/api")
    @RequiresPermissions("replenishment")
    public Result replenishment(){
        return apiService.replenishment();
    }

    @GetMapping("/inquiries/api")
    @RequiresPermissions("inquiries")
    public Result inquiries(){
        return apiService.inquiries();
    }

    @GetMapping("/prescript/api")
    @RequiresPermissions("prescript")
    public Result prescript(){
        return apiService.prescript();
    }

    @GetMapping("/registerInfo/api")
    @RequiresPermissions("registerInfo")
    public Result registerInfo(){
        return apiService.registerInfo();
    }

    @GetMapping("/registerVisi/api")
    @RequiresPermissions("registerVisi")
    public Result registerVisi(){
        return apiService.registerVisi();
    }

    @GetMapping("/jurisdiction/api")
    @RequiresPermissions("jurisdiction")
    public Result jurisdiction(){
        return apiService.jurisdiction();
    }

    @GetMapping("/resign/api")
    @RequiresPermissions("resign")
    public Result resign(){
        return apiService.resign();
    }
    @GetMapping("/infoManager/api")
    @RequiresPermissions("infoManager")
    public Result infoManager(){
        return apiService.infoManager();
    }
    @GetMapping("/duty/api")
    @RequiresPermissions("duty")
    public Result duty(){
        return apiService.duty();
    }

}
