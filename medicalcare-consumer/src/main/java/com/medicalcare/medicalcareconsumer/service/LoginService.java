package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.User;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE")
public interface LoginService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public Result login(@RequestBody(required = false) User user);

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Result logout();

    @RequestMapping(value = "/api",method = RequestMethod.GET)
    public Result api();

    @RequestMapping(value = "/unauthorized")
    public Result unauthorized(@RequestParam("code") int code, HttpServletRequest request);

    @PostMapping(value = "/verifyPassword")
    public Result verifyPassword(@RequestBody Map map);

    @PostMapping(value = "/updatePassword")
    public Result updatePassword(@RequestBody Map map);


    @GetMapping(value = "/selCurrentUser/{id}")
    public Result selUser(@PathVariable(value = "id") String uid);

    @PostMapping(value = "/updatePortrait",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result updatePortrait(@RequestPart(value = "file",required = false) MultipartFile file, @RequestParam(value = "uid") String uid);


    @PostMapping(value="updateUser")
    public Result updateUser(@RequestBody User user);
}
