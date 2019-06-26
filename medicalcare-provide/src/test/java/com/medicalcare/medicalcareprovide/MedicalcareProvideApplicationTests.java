package com.medicalcare.medicalcareprovide;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.entity.Menu;
import com.medicalcare.entity.Role;
import com.medicalcare.entity.User;
import com.medicalcare.medicalcareprovide.mapper.KcdrugsMapper;
import com.medicalcare.medicalcareprovide.mapper.MenuMapper;
import com.medicalcare.medicalcareprovide.mapper.UserMapper;
import com.medicalcare.medicalcareprovide.service.MenuService;
import com.medicalcare.medicalcareprovide.service.RoleService;
import com.medicalcare.medicalcareprovide.service.UserService;
import com.netflix.ribbon.proxy.annotation.Var;
import io.jsonwebtoken.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicalcareProvideApplicationTests {

    @Resource
    private UserService userServiceImpl;



    @Resource
    private RoleService roleServiceImpl;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private MenuService menuServiceImpl;



    @Test
    public void menu(){
        List<Menu> menus = menuServiceImpl.selAllMenu(0);
        System.out.println(menus);
    }


    @Test
    public void role(){
        List<Role> roleIPage = roleServiceImpl.selPageRple(2, null, null, null);
        System.out.println(roleIPage);

    }


    @Resource
    private UserMapper userMapper;
    @Test
    public void user(){
       User zhaohh = userMapper.selUser("zhaohh");
        System.out.println(zhaohh);
    }

    @Test
    public void password(){
        List<User> users = userMapper.selectList(null);
        for (User u : users){
            String acount = u.getAcount();
            String algorithmName = "md5";
            Object source = "123456";
            Object salt = ByteSource.Util.bytes(acount);
            int hashIterations = 1024;
            SimpleHash simpleHash = new SimpleHash(algorithmName,source, salt, hashIterations);
            System.out.println(simpleHash.toString());
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(User::getAcount,acount);
            u.setPassword(simpleHash.toString());
            userMapper.update(u,updateWrapper);
        }

    }

    //测试jjwt
    public static void main(String[] args) {
        System.out.println(Calendar.getInstance());
    }

}
