package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.User;
import com.medicalcare.util.PageResult;

import java.util.List;

public interface UserService {

    /**
     * 按照用户名进行查询
     * @param username
     * @return
     */
    User selUser(String username);
    /**
     * 得到除root外的用户信息+分页+条件
     */
    PageResult<User> getAllUser(String uid,Integer current, Integer size, String uname, String did);

    /**
     * 添加用户
     */
    boolean insertUser(User user);

    /**
     * 查询用户uid
     */
    List<String> getAlluId();


}
