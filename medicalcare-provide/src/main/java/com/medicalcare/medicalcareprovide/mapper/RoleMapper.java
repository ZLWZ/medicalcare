package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    public List<Role> selPageRole(@Param("page") long page, @Param("rname") String rname,
                                  @Param("startTime") Date startTime, @Param("endingTime") Date endingTime);


    Role selRoleId(@Param("rid") long rid);

    int updateRoleId(@Param("role") Role role);

    List<Role> getTongJi();
}
