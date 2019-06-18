package com.medicalcare.medicalcareprovide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.medicalcare.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RoleService {
    List<Role> getAllRole();

    long selCount(String rname, Date startTime, Date endingTime);

    List<Role> selPageRple(long current, String rname, Date startTime, Date endingTime);


    Role selRoleId(long id);

    boolean addRole(Role role);

    boolean updateRoleId(Role role);

    boolean deleteRole(long rid);
}
