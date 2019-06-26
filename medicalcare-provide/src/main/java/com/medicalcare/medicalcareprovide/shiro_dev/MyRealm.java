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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    private final static Logger log = LoggerFactory.getLogger(MyRealm.class);

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
        User user = (User)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String acount = user.getAcount();
        Set<String> setRole = new HashSet<String>();//角色
        Set<String> setMenu = new HashSet<String>();//权限
        log.info("进行授权");
        if(user != null){
            if(user.getMenuList() != null && !user.getMenuList().isEmpty()){
                for(Menu menu : user.getMenuList()){
                    if(StringUtils.isEmpty(menu.getLink())){
                        for(Menu menu1 : menu.getMenus()){
                            if(!StringUtils.isEmpty(menu1.getLink())){
                                System.out.println(menu1.getLink());
                                setMenu.add(menu1.getLink());
                            }
                        }
                    }else{
                        setMenu.add(menu.getLink());
                    }
                }
            }
        }
        log.info("授权成功");
        info.setStringPermissions(setMenu);//权限
        return info;
    }

    /**
     * 进行用户的认证
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        log.info("正在进行用户认证");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        User userNoe = userServiceImpl.selectOne(new User().setAcount(username));
        if(userNoe == null){
            log.info("用户认证认证失败");
            throw new UnknownAccountException("用户不存在");
        }
        User user = userServiceImpl.selUser(username);
        if(user == null){
            user = userNoe;
        }
        if(user.getMenuList() != null && !user.getMenuList().isEmpty()){
            for(Menu menu : user.getMenuList()){
                List<Menu> menus = menuServiceImpl.selMenuList(username, menu.getMid());
                menu.setMenus(menus.isEmpty() ? null : menus);
            }
        }
        ByteSource byteSource =  ByteSource.Util.bytes(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(),byteSource ,getName());
    }
}
