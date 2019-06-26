package com.medicalcare.medicalcareprovide.controller;


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
    @GetMapping("/delivery/api")
    @RequiresPermissions("delivery")
    public Result delivery(){
        return new Result(ResultCode.SUCCESS);
    }
    @GetMapping("/storehouse/api")
    @RequiresPermissions("storehouse")
    public Result storehouse(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/waste/api")
    @RequiresPermissions("waste")
    public Result waste(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/replenishment/api")
    @RequiresPermissions("replenishment")
    public Result replenishment(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/inquiries/api")
    @RequiresPermissions("inquiries")
    public Result inquiries(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/prescript/api")
    @RequiresPermissions("prescript")
    public Result prescript(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/registerInfo/api")
    @RequiresPermissions("registerInfo")
    public Result registerInfo(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/registerVisi/api")
    @RequiresPermissions("registerVisi")
    public Result registerVisi(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/jurisdiction/api")
    @RequiresPermissions("jurisdiction")
    public Result jurisdiction(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/resign/api")
    @RequiresPermissions("resign")
    public Result resign(){
        return new Result(ResultCode.SUCCESS);
    }
    @GetMapping("/infoManager/api")
    @RequiresPermissions("infoManager")
    public Result infoManager(){
        return new Result(ResultCode.SUCCESS);
    }
    @GetMapping("/duty/api")
    @RequiresPermissions("duty")
    public Result duty(){
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/doctor/api")
    @RequiresPermissions("doctor")
    public Result doctor(){
        return new Result(ResultCode.SUCCESS);
    }






}
