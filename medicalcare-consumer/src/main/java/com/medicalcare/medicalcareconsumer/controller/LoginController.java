package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.User;
import com.medicalcare.medicalcareconsumer.service.LoginService;
import com.medicalcare.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Result login(@RequestBody(required = false) User user){
        return loginService.login(user);
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Result logout(){
        return loginService.logout();
    }

    /**
     * 测试有没有权限
     * @return
     */
    @RequestMapping(value = "/api",method = RequestMethod.GET)
    public Result api(){
        return loginService.api();
    }

    /**
     * 没有权限跳转的页面
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "/unauthorized")
    public Result unauthorized(@RequestParam("code") int code, HttpServletRequest request){
        return loginService.unauthorized(code,request);
    }

    /**
     * 验证密码
     * @param map
     * @return
     */
    @PostMapping(value = "/verifyPassword")
    public Result verifyPassword(@RequestBody Map map){
        return loginService.verifyPassword(map);
    }

    /**
     * 修改密码
     * @param map
     * @return
     */
    @PostMapping(value = "/updatePassword")
    public Result updatePassword(@RequestBody Map map){
        return loginService.updatePassword(map);
    }

    /**
     * 查询当前用户
     * @param uid
     * @return
     */
    @GetMapping(value = "/selCurrentUser/{id}")
    public Result selUser(@PathVariable(value = "id") String uid){
        return loginService.selUser(uid);
    }

    /**
     * 修改头像
     * @param file
     * @param uid
     * @return
     */
    @PostMapping(value = "/updatePortrait")
    public Result updatePortrait(@RequestPart(value = "file",required = false) MultipartFile file, @RequestParam(value = "uid") String uid){
        System.out.println(file.getOriginalFilename());
        return loginService.updatePortrait(file,uid);
    }

    /**
     * 修改用户名
     * @param user
     * @return
     */
    @PostMapping(value="updateUser")
    public Result updateUser(@RequestBody User user){
        return loginService.updateUser(user);
    }

}
