package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Parti;
import com.medicalcare.medicalcareprovide.mapper.PartiMapper;
import com.medicalcare.medicalcareprovide.service.PartiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartiServiceImpl implements PartiService {
    @Autowired
    private PartiMapper partiMapper;
    @Override
    public List<Parti> getAllParti() {
        QueryWrapper<Parti> queryWrapper = new QueryWrapper<Parti>();
        queryWrapper.lambda().notIn(Parti::getPid,"6");
        return partiMapper.selectList(queryWrapper);
    }
}
