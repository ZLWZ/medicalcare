package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.User;
import com.medicalcare.entity.UserRole;
import com.medicalcare.medicalcareprovide.service.*;
import com.medicalcare.medicalcareprovide.utils.UserUtils;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("manager")
public class InfoManagerController {
    @Autowired
    private DepartmentService departmentServiceImpl;//科室表

    @Autowired
    private UserService userServiceImpl;//用户表

    @Autowired
    private RoleService roleServiceImpl;//角色表

    @Autowired
    private EducationService educationServiceImpl;//学历表

    @Autowired
    private UserRoleService userRoleServiceImpl;//用户权限表

    @RequestMapping(value = "getAllInfo",method = RequestMethod.GET)
    public  Result getAllDepartment(){
        List<List> list = new ArrayList<>();
        list.add(departmentServiceImpl.getAllDepartment());
        list.add(roleServiceImpl.getAllRole());
        list.add(educationServiceImpl.getAllEducation());
        return new Result(ResultCode.SUCCESS,list);
    }

    @RequestMapping(value = "getAllUser",method = RequestMethod.GET)
    public PageResult<User> getAllUser(@RequestParam("current") Integer current,@RequestParam("size") Integer size,@RequestParam("uname") String uname,@RequestParam("did") String did){
        PageResult<User> allUser = userServiceImpl.getAllUser(current, size, uname, did);
        System.out.println(allUser);
        return allUser;
    }

    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    public boolean insertUser(@RequestBody Map<String,String> map){
        User user = new User();
        List<String> alluId = userServiceImpl.getAlluId();
        String uid = UserUtils.getuId();
        while (true){
            if(alluId.contains(uid)){
                uid = UserUtils.getuId();
            }else{
                user.setUid(uid);
                break;
            }
        }
        user.setUname(map.get("uname"));
        user.setAcount(map.get("acount"));
        user.setAddress(map.get("address"));
        user.setPassword(UserUtils.getPassWord(user.getAcount()));
        user.setIdcard(map.get("idcard"));
        user.setPhone(map.get("phone"));
        user.setDetials(map.get("detials"));
        user.setJoindate(new Timestamp(new Date().getTime()));
        user.setAge(Long.parseLong(map.get("age")));
        user.setSex(Long.parseLong(map.get("sex")));
        user.setDid(Long.parseLong(map.get("did")));
        user.setEid(Long.parseLong(map.get("eid")));
        user.setState(1);
        boolean a = userServiceImpl.insertUser(user);
        if(a){
            UserRole userRole = new UserRole();
            userRole.setRid(Long.parseLong(map.get("rid")));
            userRole.setUid(uid);
            if(userRoleServiceImpl.insertUserRole(userRole)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

}
