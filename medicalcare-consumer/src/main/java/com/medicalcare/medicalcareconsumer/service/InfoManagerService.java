package com.medicalcare.medicalcareconsumer.service;

import com.medicalcare.entity.User;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("MEDICALCARE-PROVIDE/manager")
public interface InfoManagerService {
    //得到页面3个下拉框的内容
    @RequestMapping(method = RequestMethod.GET, value="getAllInfo")
    public Result getAllInfo();
    //得到所有用户信息
    @RequestMapping(method = RequestMethod.GET, value="getAllUser")
    public PageResult<User> getAllUser(@RequestParam("uid") String uid, @RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("uname") String uname, @RequestParam("did") String did);
    //添加用户
    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    public boolean insertUser(@RequestBody Map map);
    //修改用户
    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public boolean updateUser(@RequestBody Map map);
    //删除用户
    @RequestMapping(value = "deleteUser",method = RequestMethod.GET)
    boolean deleteUser(@RequestParam("uid") String uid);
    //删除所选中用户
    @RequestMapping(value = "deleteAllUser",method = RequestMethod.GET)
    public boolean deleteAllUser(@RequestParam("uids") String uids);
    //修改用户状态
    @RequestMapping(value = "updateUserState",method = RequestMethod.GET)
    public boolean updateUserState(@RequestParam("uid") String uid, @RequestParam("state") Long state);
    //检验身份证
    @RequestMapping(value = "checkID",method = RequestMethod.GET)
    public boolean checkID(@RequestParam("idcard") String idcard);
    //检验手机号
    @RequestMapping(value = "checkPhone",method = RequestMethod.GET)
    public boolean checkPhone(@RequestParam("phone") String phone, @RequestParam("uid") String uid);
    //检验用户名
    @RequestMapping(value = "checkAcount",method = RequestMethod.GET)
    public boolean checkAcount(@RequestParam("acount") String acount, @RequestParam("uid") String uid);
    //离职
    @RequestMapping(value = "leave",method = RequestMethod.GET)
    public boolean leave(@RequestParam("uid") String uid, @RequestParam("details") String details);
}
