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
    //得到页面3个下拉框的内容
    @RequestMapping(method = RequestMethod.GET, value="getAllInfo")
    public Result getAllInfo(){
        return infoManagerService.getAllInfo();
    }
    //得到所有用户信息
    @RequestMapping(method = RequestMethod.GET,value = "getAllUser")
    public PageResult<User> getAllUser(@RequestParam("uid")String uid,@RequestParam("current") Integer current,@RequestParam("size") Integer size,@RequestParam("uname") String uname,@RequestParam("did") String did){
        return infoManagerService.getAllUser(uid,current,size,uname,did);
    }
    //添加用户
    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    public boolean insertUser(@RequestBody Map map){
        return infoManagerService.insertUser(map);
    }

    //修改用户
    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public boolean updateUser(@RequestBody Map map){
        return infoManagerService.updateUser(map);
    }
    //修改用户状态
    @RequestMapping(value = "updateUserState",method = RequestMethod.GET)
    public boolean updateUserState(@RequestParam("uid") String uid,@RequestParam("state") Long state){
        return infoManagerService.updateUserState(uid,state);
    }
    //删除用户
    @RequestMapping(value = "deleteUser",method = RequestMethod.GET)
    public boolean deleteUser(@RequestParam("uid")String uid){
        return infoManagerService.deleteUser(uid);
    }

    //删除所选中用户
    @RequestMapping(value = "deleteAllUser",method = RequestMethod.GET)
    public boolean deleteAllUser(@RequestParam("uids") String uids){
        return infoManagerService.deleteAllUser(uids);
    }

    //检验身份证
    @RequestMapping(value = "checkID",method = RequestMethod.GET)
    public boolean checkID(@RequestParam("idcard") String idcard){
        return infoManagerService.checkID(idcard);
    }
    //检验手机号
    @RequestMapping(value = "checkPhone",method = RequestMethod.GET)
    public boolean checkPhone(@RequestParam("phone") String phone,@RequestParam("uid")String uid){
        return infoManagerService.checkPhone(phone,uid);
    }
    //检验用户名
    @RequestMapping(value = "checkAcount",method = RequestMethod.GET)
    public boolean checkAcount(@RequestParam("acount") String acount,@RequestParam("uid")String uid){
        return infoManagerService.checkAcount(acount,uid);
    }
    //离职
    @RequestMapping(value = "leave",method = RequestMethod.GET)
    public boolean leave(@RequestParam("uid") String uid,@RequestParam("details") String details){
        return infoManagerService.leave(uid,details);
    }
}
