package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.User;
import com.medicalcare.medicalcareprovide.service.UserService;
import com.medicalcare.medicalcareprovide.utils.UserUtils;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {

    @Resource
    private UserService userServiceImpl;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Value(value = "${filePath}")
    private String filePath;
    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Result login(@RequestBody(required = false) User user){
        System.out.println(user.getAcount());
        System.out.println(user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(user.getAcount(),user.getPassword());
            token.setRememberMe(true);//没什么用纯属为啦好看
            try {
                subject.login(token);
                Serializable id = subject.getSession().getId();
                Subject sub = SecurityUtils.getSubject();
                PrincipalCollection principals = sub.getPrincipals();
                User us = (User)principals.getPrimaryPrincipal();
                us.setAuthoriztion(id.toString());
                return new Result(ResultCode.SUCCESS,us);
            }catch (AuthenticationException e){
                return new Result(ResultCode.FAIL,"用户名或密码错误");
            }
        }
        return null;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Result logout(){
        System.out.println("触发logout");
        return new Result(ResultCode.SUCCESS,"已退出");
    }

    @RequestMapping(value = "/api",method = RequestMethod.GET)
    public Result api(){
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/unauthorized")
    public Result unauthorized(@RequestParam("code") int code, HttpServletRequest request) {
        String header = request.getHeader("Authoriztion");
        return code == 1 ? new Result(ResultCode.UNAUTHENTICATED) : new Result(ResultCode.UNAUTHORISE);
    }

    @PostMapping(value = "/verifyPassword")
    public Result verifyPassword(@RequestBody Map map){
        User user = userServiceImpl.selUserById(map.get("uid").toString());
        if(user.getPassword().equals(UserUtils.getPassWord(user.getAcount(),map.get("password").toString()))){
            return new Result(ResultCode.SUCCESS,"ok");
        }else{
            return new Result(ResultCode.SUCCESS,"密码错误");
        }
    }

    @PostMapping(value = "/updatePassword")
    public Result updatePassword(@RequestBody Map map){
        User user = userServiceImpl.selUserById(map.get("uid").toString());
        user.setPassword(UserUtils.getPassWord(user.getAcount(),map.get("password").toString()));
        if(userServiceImpl.updateUser(user)){
            return new Result(ResultCode.SUCCESS,"ok");
        }else{
            return new Result(ResultCode.SUCCESS,"修改失败");
        }
    }


    @GetMapping(value = "/selCurrentUser/{id}")
    public Result selUser(@PathVariable(value = "id") String uid){
        User user = userServiceImpl.selUserById(uid);
        return new Result(ResultCode.SUCCESS,user);
    }

    @PostMapping(value = "/updatePortrait")
    public Result updatePortrait(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam(value = "uid") String uid){
        try {
            if(file.isEmpty()){
                return new Result(ResultCode.FAIL,"上传失败");
            }
            String fileName = file.getOriginalFilename();
            log.info("文件名称"+fileName);
            String substring = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为"+substring);
            String path = this.filePath + fileName;
            File dect = new File(path);
            if(!dect.getParentFile().exists()){
                dect.getParentFile().mkdirs();
            }
            User user = userServiceImpl.selUserById(uid);
            user.setPortrait(fileName);
            userServiceImpl.updateUser(user);
            log.info("上传路径"+dect);
            file.transferTo(dect);
            log.info("上传成功");
            return new Result(ResultCode.SUCCESS,"ok");
        } catch (IOException e) {
            log.info("上传失败"+e);
        }
        return new Result(ResultCode.SUCCESS,"上传失败");
    }

    @PostMapping(value="updateUser")
    public Result updateUser(@RequestBody User user){
        User users = userServiceImpl.selUserById(user.getUid());
        users.setPhone(user.getPhone());
        users.setAddress(user.getAddress());
        return new Result(ResultCode.SUCCESS,userServiceImpl.updateUser(users));
    }
}
