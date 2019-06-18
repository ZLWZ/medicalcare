package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuService {

    List<Menu> selMenuList(String username, long menuId);

    List<Menu> selMenuAndRoleList(long rid, long parend);

    List<Menu> selAllMenu(long parend);

    List<Menu> selHaveAll(long rid);


}
