package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.Shift;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("MEDICALCARE-PROVIDE/manager")
public interface DutyService {
    @RequestMapping(method = RequestMethod.GET, value="getAllDuty")
    public List<Shift> getAllDuty(@RequestParam("sid") String sid, @RequestParam("uname") String uname, @RequestParam("sdate") String sdate, @RequestParam("did") String did);

    @RequestMapping(method = RequestMethod.GET, value="getAllDutyInfo")
    public Result getAllDutyInfo(@RequestParam("uid") String uid);

    @RequestMapping(value = "updateShift",method = RequestMethod.POST)
    public boolean updateShift(@RequestBody Shift shift);

    @RequestMapping(value = "insertShift",method = RequestMethod.POST)
    public boolean insertShift(@RequestBody Shift shift);

    @RequestMapping(method = RequestMethod.GET, value="deleteShift")
    public boolean deleteShift(@RequestParam("sid") long sid);
}
