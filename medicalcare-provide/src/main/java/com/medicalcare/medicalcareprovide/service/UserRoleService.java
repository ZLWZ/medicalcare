package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    boolean insertUserRole(UserRole userRole);

    List<UserRole> selAllRid(long rid);

    boolean delAllRid(long rid);


    boolean updataeUserRole(UserRole userRole);

    boolean deleteById(String uid);
}
