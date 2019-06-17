package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Menu;
import com.medicalcare.medicalcareprovide.mapper.MenuMapper;
import com.medicalcare.medicalcareprovide.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 查询角色下的所有菜单 递归查询 按照名字
     * @param username
     * @param menuId
     * @return
     */
    @Override
    public List<Menu> selMenuList(String username, long menuId) {
        return menuMapper.selMenuList(username,menuId);
    }

    /**
     * 查询角色下的所有菜单 递归查询 按照id
     * @param rid
     * @param parend
     * @return
     */
    @Override
    public List<Menu> selMenuAndRoleList(long rid, long parend) {
        return menuMapper.selMenuAndRoleList(rid,parend);
    }

    /**
     * 查询所有
     * @param parend
     * @return
     */
    @Override
    public List<Menu> selAllMenu(long parend) {
        return menuMapper.selAllMenu(parend);
    }

    /**
     * 按照角色id查询
     * @param rid
     * @return
     */
    @Override
    public List<Menu> selHaveAll(long rid) {
        return menuMapper.selHaveAll(rid);
    }


}
