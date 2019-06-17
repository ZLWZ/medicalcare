package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.User;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("MEDICALCARE-PROVIDE")
public interface LoginService {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody(required = false) User user);

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public void logout();
}
