package com.medicalcare.medicalcareprovide.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareprovide.mapper.RegisterMapper;
import com.medicalcare.medicalcareprovide.service.RegisterService;
import com.medicalcare.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterMapper registerMapper;
    @Override
    public PageResult<Register> getAllRegister(Integer end,Integer current,Integer size,String rid, String rname) {
        PageResult<Register> pageResult = new PageResult<Register>();
        pageResult.setRows(registerMapper.getAllRegister(end,(current-1)*size,size,rid,rname));
        QueryWrapper<Register> queryWrapper = new QueryWrapper<Register>();
        queryWrapper.lambda().between(Register::getRstatic,1,2);
        if(!StringUtils.isEmpty(rid))queryWrapper.lambda().like(Register::getRid,rid);
        if(!StringUtils.isEmpty(rname))queryWrapper.lambda().like(Register::getRname,rname);
        pageResult.setTotal(registerMapper.selectCount(queryWrapper).longValue());
        return pageResult;
    }

    @Override
    public Register getRegister(String rid) {
        return registerMapper.selectById(rid);
    }

    @Override
    public boolean updateRegister(Register register) {
        return registerMapper.updateById(register)>0;
    }

    @Override
    public boolean updateRstatic(String rid,Integer rstatic,Double rprice) {
        return registerMapper.updateRegister(rid,rstatic,rprice);
    }

    @Override
    public boolean addRegister(Register register) {
        return registerMapper.insert(register)>0;
    }

    @Override
    public boolean updatePstate(String rid,Double rprice,long pstate) {
        return registerMapper.updatePstate(rid,rprice,pstate);
    }

    @Override
    public List<Integer> getReg(Map<String, Object> map, long pstate) {
        String data = JSONArray.toJSONString(map.get("data"));
        List<String> list = JSON.parseArray(data, String.class);
        List<Integer> ll = new ArrayList<>();
        for (String s : list){
            ll.add( registerMapper.selectCount(new QueryWrapper<Register>().lambda().likeRight(Register::getRedate, s).eq(Register::getPstate,pstate)));
        }
        return ll;
    }
}
