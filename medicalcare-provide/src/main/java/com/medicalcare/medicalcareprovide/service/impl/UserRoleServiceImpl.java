package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.medicalcare.entity.UserRole;
import com.medicalcare.medicalcareprovide.mapper.UserRoleMapper;
import com.medicalcare.medicalcareprovide.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public boolean insertUserRole(UserRole userRole) {
        return userRoleMapper.insert(userRole)>1;
    }

    @Override
    public List<UserRole> selAllRid(long rid) {
        return userRoleMapper.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getRid,rid));
    }

    @Override
    public boolean delAllRid(long rid) {
        return userRoleMapper.delete(new UpdateWrapper<UserRole>().lambda().eq(UserRole::getRid,rid)) > 0;
    }
}
