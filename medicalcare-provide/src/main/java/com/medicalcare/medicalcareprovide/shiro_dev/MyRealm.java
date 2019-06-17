package com.medicalcare.medicalcareprovide.shiro_dev;

import com.medicalcare.entity.Menu;
import com.medicalcare.entity.Role;
import com.medicalcare.entity.User;
import com.medicalcare.medicalcareprovide.mapper.MenuMapper;
import com.medicalcare.medicalcareprovide.service.MenuService;
import com.medicalcare.medicalcareprovide.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    public void setName(String name){
        super.setName("cyRealm");
    }


    @Resource
    private UserService userServiceImpl;

    @Resource
    private MenuService menuServiceImpl;

    /**
     * 进行用户的授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.getPrimaryPrincipal();SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String acount = user.getAcount();
        Set<String> setRole = new HashSet<String>();//角色
        Set<String> setMenu = new HashSet<String>();//权限
        for (Role role : user.getRoleList()){
            setRole.add(role.getRname());
        }
        for(Menu menu : user.getMenuList()){
            if(StringUtils.isEmpty(menu.getLink())){
                for(Menu menu1 : menu.getMenus()){
                    if(!StringUtils.isEmpty(menu1.getLink())){
                        setMenu.add(menu.getLink());
                    }
                }
            }else{
                setMenu.add(menu.getLink());
            }

        }
        info.addRoles(setRole);//角色
        info.addStringPermissions(setMenu);//权限
        return info;
    }

    /**
     * 进行用户的认证
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        User user = userServiceImpl.selUser(username);
        for(Menu menu : user.getMenuList()){
            List<Menu> menus = menuServiceImpl.selMenuList(username, menu.getMid());
            menu.setMenus(menus.isEmpty() ? null : menus);
        }
        if(user == null){
            throw new UnknownAccountException("用户不存在");
        }
        ByteSource byteSource =  ByteSource.Util.bytes(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(),byteSource ,getName());
    }
}
