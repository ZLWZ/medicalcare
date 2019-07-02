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
    public Result delivery(){
        return apiService.delivery();
    }
    @GetMapping("/storehouse/api")
    public Result storehouse(){
        return apiService.storehouse();
    }
    @GetMapping("/waste/api")
    public Result waste(){
        return apiService.waste();
    }
    @GetMapping("/replenishment/api")
    public Result replenishment(){
        return apiService.replenishment();
    }
    @GetMapping("/inquiries/api")
    public Result inquiries(){
        return apiService.inquiries();
    }
    @GetMapping("/prescript/api")
    public Result prescript(){
        return apiService.prescript();
    }
    @GetMapping("/registerInfo/api")
    public Result registerInfo(){
        return apiService.registerInfo();
    }
    @GetMapping("/registerVisi/api")
    public Result registerVisi(){
        return apiService.registerVisi();
    }
    @GetMapping("/jurisdiction/api")
    public Result jurisdiction(){
        return apiService.jurisdiction();
    }
    @GetMapping("/resign/api")
    public Result resign(){
        return apiService.resign();
    }
    @GetMapping("/infoManager/api")
    public Result infoManager(){
        return apiService.infoManager();
    }
    @GetMapping("/duty/api")
    public Result duty(){
        return apiService.duty();
    }
    @GetMapping("/doctor/api")
    public Result doctor(){
        return apiService.doctor();
    }
}
