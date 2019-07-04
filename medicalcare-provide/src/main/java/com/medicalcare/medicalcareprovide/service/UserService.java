package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.User;
import com.medicalcare.util.PageResult;

import java.util.List;

public interface UserService {

    User selectOne(User user);
    /*按照用户名进行查询*/
    User selUser(String username);
    /*得到除root外的用户信息+分页+条件*/
    PageResult<User> getAllUser(String uid, Integer current, Integer size, String uname, String did);
    /*添加用户*/
    boolean insertUser(User user);
    /*查询用户uid*/
    List<String> getAlluId();
    /*修改用户*/
    boolean updateUser(User user);
    /*修改用户状态*/
    boolean updateUserState(String uid, Long state);
    /*删除用户*/
    boolean deleteUser(String uid);
    /*根据ids查询用户*/
    List<User> getAllUserByuid(List<String> uids);
    /*得到所有医生护士信息*/
    List<User> getUserByRid(String uid);
    /*得到所有身份证*/
    List<String> getAllID();
    /*得到所有手机号*/
    List<String> getAllPhone(String uid);
    /*得到所有用户名*/
    List<String> getAllAcount(String uid);
    /*查询指定的用户*/
    User selUserById(String uid);
    /*根据科室得到医生*/
    List<User> getUserByDid(Long did,Long rid);
    /*得到人员流动表中的数据*/
    User getInfoUser(String uid);

    List<User> getTongJi();
}
