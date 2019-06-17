package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@FeignClient(value = "MEDICALCARE-PROVIDE/jurisdiction")
public interface JurisdictionService {


    /**
     * 查询角色
     * @param rname
     * @param startTime
     * @param endingTime
     * @param current
     * @return
     */
    @RequestMapping(value = "/selAllRole",method = RequestMethod.GET)
    public Result selAllRole(@RequestParam(value = "rname",required = false) String rname,
                             @RequestParam(value = "startTime",required = false) String startTime,
                             @RequestParam(value = "endingTime",required = false) String endingTime,
                             @RequestParam(value = "current",required = false) Integer current);

    /**
     * 按照id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/selRoleId/{id}",method = RequestMethod.GET)
    public Result selRoleId(@PathVariable("id") long id);

    /**
     * 查询所有的菜单
     * @return
     */
    @RequestMapping(value = "/selAllMenu",method = RequestMethod.GET)
    public Result selRoleId();

    /**
     * 按照id查询菜单
     * @return
     */
    @RequestMapping(value = "/selAllMenu/{rid}",method = RequestMethod.GET)
    public Result selUpdateRoleId(@PathVariable(value = "rid") int rid);

    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public Result addRole(@RequestBody Map map);

    /**
     * 修改角色信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/updRoleMenu",method = RequestMethod.PUT)
    public Result updRoleMenu(@RequestBody Map map);

    @RequestMapping(value = "/deleteRole/{rid}",method = RequestMethod.DELETE)
    public Result deleteRole(@PathVariable(value = "rid") long rid);
}
