package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.User;
import com.medicalcare.medicalcareconsumer.service.LoginService;
import com.medicalcare.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody(required = false) User user){
        return this.loginService.login(user);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public void logout(){
        this.loginService.logout();
    }

}
