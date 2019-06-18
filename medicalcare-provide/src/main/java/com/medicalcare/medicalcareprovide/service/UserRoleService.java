package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    /**
     * 添加
     * @param userRole
     * @return
     */
    boolean insertUserRole(UserRole userRole);

    /**
     * 查询 rid
     * @param rid
     * @return
     */
    List<UserRole> selAllRid(long rid);

    /**
     * 删除
     * @param rid
     * @return
     */
    boolean delAllRid(long rid);


    boolean updataeUserRole(UserRole userRole);

    boolean deleteById(String uid);
}
