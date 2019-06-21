package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Information;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE/manager")
public interface ResignService {
    //得到人员流动表信息
    @RequestMapping(method = RequestMethod.GET,value = "/getAllInformation")
    public List<Information> getAllInformation();
}
