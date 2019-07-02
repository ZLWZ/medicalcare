package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Specifi;
import com.medicalcare.medicalcareprovide.mapper.SpecifiMapper;
import com.medicalcare.medicalcareprovide.service.SpecifiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpecifiServiceImpl implements SpecifiService {

    @Resource
    private SpecifiMapper specifiMapper;

    @Override
    public List<Specifi> selAllSpecifi() {
        return specifiMapper.selectList(null);
    }

    @Override
    public Specifi selBysid(long sid) {
        return specifiMapper.selectOne(new QueryWrapper<Specifi>().lambda().eq(Specifi::getSid,sid));
    }
}
