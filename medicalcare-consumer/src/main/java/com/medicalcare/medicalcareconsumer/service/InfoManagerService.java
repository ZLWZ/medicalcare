package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Department;
import com.medicalcare.entity.Role;
import com.medicalcare.entity.User;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE/manager")
public interface InfoManagerService {
    @RequestMapping(method = RequestMethod.GET, value="getAllInfo")
    public Result getAllInfo();

    @RequestMapping(method = RequestMethod.GET, value="getAllUser")
    public PageResult<User> getAllUser(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("uname") String uname, @RequestParam("did") String did);

    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    public boolean insertUser(@RequestBody Map map);
}
