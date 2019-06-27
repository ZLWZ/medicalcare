package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Pregdetils;
import com.medicalcare.medicalcareprovide.mapper.PregdetilsMapper;
import com.medicalcare.medicalcareprovide.service.PregdetilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PregdetilsServiceImpl implements PregdetilsService {
    @Autowired
    private PregdetilsMapper pregdetilsMapper;
    @Override
    public boolean addPregdetils(Pregdetils pregdetils) {
        return pregdetilsMapper.insert(pregdetils)>0;
    }

    @Override
    public List<Pregdetils> getAllPregdetils(String rid) {
        QueryWrapper<Pregdetils> queryWrapper = new QueryWrapper<Pregdetils>();
        queryWrapper.lambda().eq(Pregdetils::getRid,rid);
        return pregdetilsMapper.selectList(queryWrapper);
    }
}
