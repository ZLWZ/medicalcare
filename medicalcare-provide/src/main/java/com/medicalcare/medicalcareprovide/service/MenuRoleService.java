package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.MenuRole;

import java.util.List;

public interface MenuRoleService {

    boolean addMenuRole(MenuRole menuRole);

    List<MenuRole> selAllHave(List<Integer> ids,int rid);

    boolean delMenuRole(List<Integer> ids,int rid);

    List<MenuRole> selAll(int rid);

    boolean delAll(long rid);

}
