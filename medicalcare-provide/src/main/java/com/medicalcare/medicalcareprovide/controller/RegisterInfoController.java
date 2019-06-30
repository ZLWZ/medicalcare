package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Information;
import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareprovide.mapper.RegisterMapper;
import com.medicalcare.medicalcareprovide.service.*;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cashier")
@RequiresPermissions("cashier")
public class RegisterInfoController {
    @Autowired
    private RegisterService registerServiceImpl;
    @Autowired
    private DepartmentService departmentServiceImpl;
    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private PregdetilsService pregdetilsServiceImpl;
    @RequestMapping(method = RequestMethod.GET,value = "getAllRegister")
    public PageResult<Register> getAllRegister(@RequestParam("end") Integer end,@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("rid") String rid, @RequestParam("rname") String rname){
        PageResult<Register> allRegister = registerServiceImpl.getAllRegister(end, current, size, rid, rname);
        List<Register> rows = allRegister.getRows();
        for(Register r : rows){
            r.setPregdetils(pregdetilsServiceImpl.getAllPregdetils(r.getRid()));
        }
        return allRegister;
    }
    @RequestMapping(value = "getAllInfo",method = RequestMethod.GET)

    public Result getAllInfo(@RequestParam("did") Long did){
        List<List> lists = new ArrayList<List>();
        lists.add(departmentServiceImpl.getAllDepartment());
        lists.add(userServiceImpl.getUserByDid(did,6L));
        return new Result(ResultCode.SUCCESS,lists);
    }

    @RequestMapping(value = "getRegister",method = RequestMethod.GET)
    public Register getRegister(@RequestParam("rid") String rid){
        return registerServiceImpl.getRegister(rid);
    }

    @RequestMapping(value = "updateRegister",method = RequestMethod.POST)
    public boolean updateRegister(@RequestBody Register register){
        return registerServiceImpl.updateRegister(register);
    }

    @RequestMapping(value = "deleteRegister",method = RequestMethod.GET)
    public boolean deleteRegister(@RequestParam("rid") String rid){
        return registerServiceImpl.updateRstatic(rid,3,0D);
    }
}
