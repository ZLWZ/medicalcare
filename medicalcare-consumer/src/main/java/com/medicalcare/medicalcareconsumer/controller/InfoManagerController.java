package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.entity.Department;
import com.medicalcare.entity.Role;
import com.medicalcare.entity.User;
import com.medicalcare.medicalcareconsumer.service.InfoManagerService;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class InfoManagerController {
    @Resource
    private InfoManagerService infoManagerService;

    @RequestMapping(method = RequestMethod.GET, value="getAllInfo")
    public Result getAllDepartment(){
        return infoManagerService.getAllInfo();
    }

    @RequestMapping(method = RequestMethod.GET,value = "getAllUser")
    public PageResult<User> getAllUser(@RequestParam("current") Integer current,@RequestParam("size") Integer size,@RequestParam("uname") String uname,@RequestParam("did") String did){
        return infoManagerService.getAllUser(current,size,uname,did);
    }
    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    public boolean insertUser(@RequestBody Map map){
        return infoManagerService.insertUser(map);
    }
}
