package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {


    User selUser(@Param("name") String name);

    //得到除最高权限外的所有用户,分页+条件查询
    List<User> getAllUser(@Param("uid") String uid, @Param("current") Integer current, @Param("size") Integer size, @Param("uname") String uname, @Param("did") Integer did);
    //得到所有uid
    List<String> getAlluId();


    //得到所有身份证
    List<String> getAllIdcard();
    //得到所有手机号
    List<String> getAllphone(@Param("uid") String uid);
    //得到所有用户名
    List<String> getAllacount(@Param("uid") String uid);
    //根据uid获得4个基本信息和科室
    List<User> getAllUserByuid(@Param("uids") List<String> uids);
    //得到所有的护士医生
    List<User> getUserByRid(@Param("uid") String uid, @Param("rids") Integer... rids);
    //修改用户启用禁用状态
    boolean updateUserState(@Param("uid") String uid, @Param("state") Long state);

//    boolean updateUser(@Param("user") User user);
}
