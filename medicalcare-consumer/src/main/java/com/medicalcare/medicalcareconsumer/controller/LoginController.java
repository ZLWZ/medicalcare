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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@RestController
@CrossOrigin
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

    @PostMapping(value = "/verifyPassword")
    public Result verifyPassword(@RequestBody Map map){
        return loginService.verifyPassword(map);
    }

    @PostMapping(value = "/updatePassword")
    public Result updatePassword(@RequestBody Map map){
        return loginService.updatePassword(map);
    }


    @GetMapping(value = "/selCurrentUser/{id}")
    public Result selUser(@PathVariable(value = "id") String uid){
        return loginService.selUser(uid);
    }

    @PostMapping(value = "/updatePortrait")
    public Result updatePortrait(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam(value = "uid") String uid){
        return loginService.updatePortrait(file,uid);
    }

    @PostMapping(value="updateUser")
    public Result updateUser(@RequestBody User user){
        return loginService.updateUser(user);
    }

}
