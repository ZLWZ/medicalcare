package com.medicalcare.medicalcareconsumer.controller;

import com.medicalcare.medicalcareconsumer.service.JurisdictionService;
import com.medicalcare.util.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/jurisdiction")
@CrossOrigin
public class JurisdictionController {

    @Resource
    private JurisdictionService jurisdictionService;


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
                             @RequestParam(value = "current",required = false) Integer current){
        return jurisdictionService.selAllRole(rname,startTime,endingTime,current);
    }

    /**
     * 按照id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/selRoleId/{id}",method = RequestMethod.GET)
    public Result selRoleId(@PathVariable("id") long id){
        return jurisdictionService.selRoleId(id);
    }

    /**
     * 查询所有的菜单
     * @return
     */
    @RequestMapping(value = "/selAllMenu",method = RequestMethod.GET)
    public Result selRoleId(){
        return jurisdictionService.selRoleId();
    }

    /**
     * 按照id查询菜单
     * @return
     */
    @RequestMapping(value = "/selAllMenu/{rid}",method = RequestMethod.GET)
    public Result selUpdateRoleId(@PathVariable(value = "rid") int rid){
        return jurisdictionService.selUpdateRoleId(rid);
    }

    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public Result addRole(@RequestBody Map map){
        return jurisdictionService.addRole(map);
    }

    /**
     * 修改角色信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/updRoleMenu",method = RequestMethod.PUT)
    public Result updRoleMenu(@RequestBody Map map){
        return jurisdictionService.updRoleMenu(map);
    }

    @RequestMapping(value = "/deleteRole/{rid}",method = RequestMethod.DELETE)
    public Result deleteRole(@PathVariable(value = "rid") long rid){
        return jurisdictionService.deleteRole(rid);
    }

}
