package com.medicalcare.medicalcareprovide.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Apply;
import com.medicalcare.entity.Department;
import com.medicalcare.entity.Specifi;
import com.medicalcare.medicalcareprovide.mapper.ApplyMapper;
import com.medicalcare.medicalcareprovide.mapper.DepartmentMapper;
import com.medicalcare.medicalcareprovide.mapper.SpecifiMapper;
import com.medicalcare.medicalcareprovide.service.ApplyService;
import com.medicalcare.medicalcareprovide.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private SpecifiMapper specifiMapper;
    @Override
    public boolean insertApply(Map<String, Object> map) {
        String str = JSON.toJSONString(map.get("apply"));
        Apply apply = JSON.parseObject(str, Apply.class);
        apply.setMkdate(new Timestamp(new Date().getTime()));
        apply.setState(1L);
        return applyMapper.insert(apply)>0;
    }

    @Override
    public List<Apply> getAllApply() {
        List<Apply> applies = applyMapper.selectList(null);
        for(Apply apply :applies){
            apply.setDepartment(departmentMapper.selectOne(new QueryWrapper<Department>().lambda().eq(Department::getDid,apply.getDid())));
            apply.setSpecifi(specifiMapper.selectOne(new QueryWrapper<Specifi>().lambda().eq(Specifi::getSid,apply.getSid())));
        }
        return applies;
    }
}
