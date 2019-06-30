package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Procdetils;
import com.medicalcare.medicalcareprovide.mapper.ProcdetilsMapper;
import com.medicalcare.medicalcareprovide.service.ProcdetilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class ProcdetilsServiceImpl implements ProcdetilsService {

    @Autowired
    private ProcdetilsMapper procdetilsMapper;
    @Override
    public boolean addProcdetils(Procdetils procdetils) {
        return procdetilsMapper.insert(procdetils)>0;
    }

    @Override
    public List<Procdetils> selProcdetils(String rid) {
        QueryWrapper<Procdetils> queryWrapper = new QueryWrapper<Procdetils>();
        queryWrapper.lambda().eq(Procdetils::getRid,rid);
        return procdetilsMapper.selectList(queryWrapper);
    }
}
