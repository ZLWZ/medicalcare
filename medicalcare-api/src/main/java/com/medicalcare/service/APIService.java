package com.medicalcare.service;

import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "MEDICALCARE-PROVIDE")
public interface APIService {
    @GetMapping("/delivery/api")
    public Result delivery();
    @GetMapping("/storehouse/api")
    public Result storehouse();
    @GetMapping("/waste/api")
    public Result waste();
    @GetMapping("/replenishment/api")
    public Result replenishment();
    @GetMapping("/inquiries/api")
    public Result inquiries();
    @GetMapping("/prescript/api")
    public Result prescript();
    @GetMapping("/registerInfo/api")
    public Result registerInfo();
    @GetMapping("/registerVisi/api")
    public Result registerVisi();
    @GetMapping("/jurisdiction/api")
    public Result jurisdiction();
    @GetMapping("/resign/api")
    public Result resign();
    @GetMapping("/infoManager/api")
    public Result infoManager();
    @GetMapping("/duty/api")
    public Result duty();


}
