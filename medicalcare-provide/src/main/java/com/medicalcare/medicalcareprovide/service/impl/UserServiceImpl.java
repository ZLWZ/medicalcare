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
    public PageResult<User> getAllUser(String uid,Integer current, Integer size, String uname, String did) {
        PageResult<User> pageResult = new PageResult<User>();
        pageResult.setRows(userMapper.getAllUser(uid,(current-1)*size,size,uname,did.equals("")||did == null?0:Integer.parseInt(did)));
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.lambda().notIn(User::getUid,"060212");
        queryWrapper.lambda().between(User::getState,1,2);
        if(uname!=null&&!uname.equals("")){
            queryWrapper.lambda().like(User::getUname,uname);
        }
        if(did!=null&&!did.equals("")) {
            queryWrapper.lambda().eq(User::getDid, Integer.parseInt(did));
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
