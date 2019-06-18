package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {


    User selUser(@Param("name") String name);

    //得到除最高权限外的所有用户,分页+条件查询
    List<User> getAllUser(@Param("uid") String uid,@Param("current") Integer current,@Param("size") Integer size,@Param("uname") String uname,@Param("did") Integer did);
    //得到所有uid
    List<String> getAlluId();
}
