package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.User;
import com.medicalcare.medicalcareconsumer.service.LoginService;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Result login(@RequestBody(required = false) User user){
        System.out.println(user);
        return loginService.login(user);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Result logout(){
        return loginService.logout();
    }

    @RequestMapping(value = "/api",method = RequestMethod.GET)
    public Result api(){
        return loginService.api();
    }

    @RequestMapping(value = "/unauthorized")
    public Result unauthorized(@RequestParam("code") int code, HttpServletRequest request){

        return loginService.unauthorized(code,request);
    }

}
