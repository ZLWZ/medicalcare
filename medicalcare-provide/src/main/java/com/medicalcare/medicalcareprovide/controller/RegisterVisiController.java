package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareprovide.service.RegisterService;
import com.medicalcare.medicalcareprovide.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("cashier")
@RequiresPermissions("cashier")
public class RegisterVisiController {
    @Autowired
    private RegisterService registerServiceImpl;
    @RequestMapping(value = "addRegister",method = RequestMethod.POST)
    public boolean addRegister(@RequestBody Register register){
        register.setRid(UserUtils.getrId());
        register.setRedate(new Timestamp(new Date().getTime()));
        register.setRprice(0);
        register.setRstatic(1L);
        register.setPstate(0L);
        return registerServiceImpl.addRegister(register);
    }
}
