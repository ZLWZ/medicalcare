package com.medicalcare.medicalcareprovide.controller;

import com.alibaba.fastjson.JSON;
import com.medicalcare.entity.Information;
import com.medicalcare.entity.User;
import com.medicalcare.entity.UserRole;
import com.medicalcare.medicalcareprovide.service.*;
import com.medicalcare.medicalcareprovide.utils.UserUtils;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("manager")
@RequiresPermissions(value = "/manager")
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

    @Autowired
    private InformationService informationServiceImpl;//离职表

    //得到页面3个下拉框的内容
    @RequestMapping(value = "getAllInfo",method = RequestMethod.GET)
    public  Result getAllDepartment(){
        List<List> list = new ArrayList<>();
        list.add(departmentServiceImpl.getAllDepartment());
        list.add(roleServiceImpl.getAllRole());
        list.add(educationServiceImpl.getAllEducation());
        return new Result(ResultCode.SUCCESS,list);
    }
    //得到所有用户信息
    @RequestMapping(value = "getAllUser",method = RequestMethod.GET)
    public PageResult<User> getAllUser(@RequestParam("uid") String uid,@RequestParam("current") Integer current,@RequestParam("size") Integer size,@RequestParam("uname") String uname,@RequestParam("did") String did){
        PageResult<User> allUser = userServiceImpl.getAllUser( uid,current, size, uname, did);
        return allUser;
    }
    //添加用户
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
        user.setPassword(UserUtils.getPassWord(user.getAcount(),"123456"));
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
    //修改用户
    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public boolean updateUser(@RequestBody Map<String,String> map){
        User user = new User();
        user.setUid(map.get("uid"));
        user.setUname(map.get("uname"));
        user.setAcount(map.get("acount"));
        user.setAddress(map.get("address"));
        user.setIdcard(map.get("idcard"));
        user.setPhone(map.get("phone"));
        user.setDetials(map.get("detials"));
        user.setAge(Long.parseLong(map.get("age")));
        user.setSex(Long.parseLong(map.get("sex")));
        user.setDid(Long.parseLong(map.get("did")));
        user.setEid(Long.parseLong(map.get("eid")));
        user.setState(1);
        boolean a = userServiceImpl.updateUser(user);
        if(a){
            UserRole userRole = new UserRole();
            userRole.setRid(Long.parseLong(map.get("rid")));
            userRole.setUid(map.get("uid"));
            if(userRoleServiceImpl.updataeUserRole(userRole)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    //修改用户状态
    @RequestMapping(value = "updateUserState",method = RequestMethod.GET)
    public boolean updateUserState(@RequestParam("uid") String uid,@RequestParam("state") Long state){
        return userServiceImpl.updateUserState(uid,state);
    }
    //删除用户
    @RequestMapping(value = "deleteUser",method = RequestMethod.GET)
    public boolean deleteUser(@RequestParam("uid") String uid){
        return userServiceImpl.deleteUser(uid);
    }
    //删除所选中用户
    @RequestMapping(value = "deleteAllUser",method = RequestMethod.GET)
    public boolean deleteAllUser(@RequestParam("uids") String uids){
        String[] split = uids.split(";");
        for (int i = 0;i<split.length;i++){
            if(!userServiceImpl.deleteUser(split[i])){
                return false;
            }
        }
        return true;
    }
    //检验身份证
    @RequestMapping(value = "checkID",method = RequestMethod.GET)
    public boolean checkID(@RequestParam("idcard") String idcard){
        return userServiceImpl.getAllID().contains(idcard);
    }
    //检验手机号
    @RequestMapping(value = "checkPhone",method = RequestMethod.GET)
    public boolean checkPhone(@RequestParam("phone") String phone,@RequestParam("uid")String uid){
        return userServiceImpl.getAllPhone(uid).contains(phone);
    }
    //检验用户名
    @RequestMapping(value = "checkAcount",method = RequestMethod.GET)
    public boolean checkAcount(@RequestParam("acount") String acount,@RequestParam("uid")String uid){
        return userServiceImpl.getAllAcount(uid).contains(acount);
    }
    //办理离职
    @RequestMapping(value = "leave",method = RequestMethod.GET)
    public boolean leave(@RequestParam("uid") String uid,@RequestParam("details") String details){
        Information information = new Information();
        information.setUid(uid);
        information.setDetails(details);
        information.setLeavedate(new Timestamp(new Date().getTime()));
        boolean a = informationServiceImpl.updateInformation(information);
        if(a){
            userServiceImpl.updateUserState(uid,3L);
            userRoleServiceImpl.deleteById(uid);
            return true;
        }else{
            return false;
        }
    }
}
