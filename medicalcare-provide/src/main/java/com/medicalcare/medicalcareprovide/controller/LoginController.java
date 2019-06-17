package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Menu;
import com.medicalcare.entity.User;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class LoginController {

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
    public Result unauthorized(@RequestParam("code") int code, HttpServletRequest request)
    {
        String header = request.getHeader("Authoriztion");
        return code == 1 ? new Result(ResultCode.UNAUTHENTICATED) : new Result(ResultCode.UNAUTHORISE);
    }
}