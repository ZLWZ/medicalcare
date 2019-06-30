package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.mapper.DrugsMapper;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DrugsServiceImpl implements DrugsService {
    @Autowired
    private DrugsMapper drugsMapper;
    @Override
    public List<Drugs> getAllDrugs(String dname) {
        if(StringUtils.isEmpty(dname)){
            return drugsMapper.selectList(null);
        }
        QueryWrapper<Drugs> queryWrapper = new QueryWrapper<Drugs>();
        queryWrapper.lambda().like(Drugs::getDname,dname);
        return drugsMapper.selectList(queryWrapper);
    }

    @Override
    public Drugs getDrugsByid(long did) {
        return drugsMapper.selectById(did);
    }

    @Override
    public boolean updateDrugs(Drugs drugs) {
        return drugsMapper.updateById(drugs)>0;
    }
}
