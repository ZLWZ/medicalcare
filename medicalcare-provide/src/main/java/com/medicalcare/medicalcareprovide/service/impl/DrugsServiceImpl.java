package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.mapper.DrugsMapper;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.util.PageResult;
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
    public PageResult<Drugs> getAllDrugs(Drugs drugs, Integer current, Integer size) {
        PageResult<Drugs> result = new PageResult<Drugs>();
        result.setRows(drugsMapper.getAllDrugs(drugs.getDname(),drugs.getDtype(),drugs.getCid(),drugs.getDid(),drugs.getSid(),current,size));
        QueryWrapper<Drugs> queryWrapper = new QueryWrapper<Drugs>();
        if(!StringUtils.isEmpty(drugs.getDname()))queryWrapper.lambda().like(Drugs::getDname,drugs.getDname());
        if(drugs.getCid()!= 0)queryWrapper.lambda().eq(Drugs::getCid,drugs.getCid());
        if(drugs.getDid()!= 0)queryWrapper.lambda().eq(Drugs::getDid,drugs.getDid());
        if(drugs.getSid()!= 0)queryWrapper.lambda().eq(Drugs::getSid,drugs.getSid());
        result.setTotal(drugsMapper.selectCount(queryWrapper).longValue());
        return result;
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
