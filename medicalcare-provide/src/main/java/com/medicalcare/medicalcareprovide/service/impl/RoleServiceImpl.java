package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicalcare.entity.Role;
import com.medicalcare.medicalcareprovide.mapper.RoleMapper;
import com.medicalcare.medicalcareprovide.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRole() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        queryWrapper.notIn("rid",1);
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public long selCount(String rname, Date startTime,Date endingTime) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        LambdaQueryWrapper<Role> lambda = queryWrapper.lambda();
        if(startTime != null)lambda.le(Role::getChangeTime,startTime);
        if(endingTime != null)lambda.ge(Role::getChangeTime,endingTime);
        if(!StringUtils.isEmpty(rname)) lambda.eq(Role::getRname,rname);
        return roleMapper.selectCount(lambda).longValue();
    }

    @Override
    public List<Role> selPageRple(long current, String rname, Date startTime, Date endingTime) {
        List<Role> roles = roleMapper.selPageRole((current - 1) * 5, rname, startTime, endingTime);
        return roles;
    }

    @Override
    public Role selRoleId(long id) {
        return roleMapper.selRoleId(id);
    }

    @Override
    public boolean addRole(Role role) {
        return roleMapper.insert(role) > 0;
    }

    @Override
    public boolean updateRoleId(Role role) {
        return roleMapper.updateRoleId(role) > 0;
    }

    @Override
    public boolean deleteRole(long rid) {
        return roleMapper.delete(new UpdateWrapper<Role>().lambda().eq(Role::getRid,rid)) > 0;
    }


}
