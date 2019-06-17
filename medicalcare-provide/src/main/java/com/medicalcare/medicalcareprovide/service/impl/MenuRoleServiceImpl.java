package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.medicalcare.entity.MenuRole;
import com.medicalcare.medicalcareprovide.mapper.MenuRoleMapper;
import com.medicalcare.medicalcareprovide.service.MenuRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class MenuRoleServiceImpl implements MenuRoleService {

    @Resource
    private MenuRoleMapper menuRoleMapper;

    /**
     * 添加
     * @param menuRole
     * @return
     */
    @Override
    public boolean addMenuRole(MenuRole menuRole) {
        return menuRoleMapper.insert(menuRole) > 0;
    }

    /**
     * 查询
     * @param ids
     * @param rid
     * @return
     */
    @Override
    public List<MenuRole> selAllHave(List<Integer> ids,int rid) {
        QueryWrapper<MenuRole> queryWrapper = new QueryWrapper<MenuRole>();
        queryWrapper.lambda().and(obj -> obj.eq(MenuRole::getRid,rid).in(MenuRole::getMid,ids));
        return menuRoleMapper.selectList(queryWrapper);
    }

    /**
     * 删除
     * @param ids
     * @param rid
     * @return
     */
    @Override
    public boolean delMenuRole(List<Integer> ids, int rid) {
        UpdateWrapper<MenuRole> updateWrapper = new UpdateWrapper<MenuRole>();
        updateWrapper.lambda().and(obj -> obj.eq(MenuRole::getRid,rid).notIn(MenuRole::getMid,ids));
        return menuRoleMapper.delete(updateWrapper) > 0;
    }

    /**
     * 查询
     * @param rid
     * @return
     */
    @Override
    public List<MenuRole> selAll(int rid) {
        return menuRoleMapper.selectList(new QueryWrapper<MenuRole>().lambda().eq(MenuRole::getRid,rid));
    }

    /**
     * 按照id删除
     * @param rid
     * @return
     */
    @Override
    public boolean delAll(long rid) {
        return menuRoleMapper.delete(new UpdateWrapper<MenuRole>().lambda().eq(MenuRole::getRid,rid)) > 0;
    }



}
