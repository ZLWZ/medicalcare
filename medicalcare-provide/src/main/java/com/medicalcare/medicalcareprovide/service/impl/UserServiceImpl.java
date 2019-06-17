package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.User;
import com.medicalcare.medicalcareprovide.mapper.UserMapper;
import com.medicalcare.medicalcareprovide.service.UserService;
import com.medicalcare.util.PageResult;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 按照用户名进行查询
     * @param username
     * @return
     */
    @Override
    public User selUser(String username) {
        return userMapper.selUser(username);
    }

    @Override
    public PageResult<User> getAllUser(Integer current, Integer size, String uname, String did) {
        PageResult<User> pageResult = new PageResult<User>();
        pageResult.setRows(userMapper.getAllUser((current-1)*size,size,uname,did.equals("")||did == null?0:Integer.parseInt(did)));
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.notIn("uid","060212");
        if(uname!=null&&!uname.equals("")){
            queryWrapper.like("uname",uname);
        }
        if(did!=null&&!did.equals("")) {
            queryWrapper.eq("did", Integer.parseInt(did));
        }
        pageResult.setTotal(userMapper.selectCount(queryWrapper).longValue());
        return pageResult;
    }

    @Override
    public boolean insertUser(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    public List<String> getAlluId() {
        return userMapper.getAlluId();
    }
}
