package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    public List<Menu> selMenuList(String username,long menuId);


    List<Menu> selMenuAndRoleList(@Param("rid") long rid,@Param("parend") long parend);


    List<Menu> selAllMenu(@Param("parend") long parend);

    List<Menu> selHaveAll(@Param("rid") long rid);
}
