package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Information;
import com.medicalcare.medicalcareprovide.mapper.InformationMapper;
import com.medicalcare.medicalcareprovide.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationMapper informationMapper;
    @Override
    public boolean updateInformation(Information information) {
        QueryWrapper<Information> queryWrapper = new QueryWrapper<Information>();
        queryWrapper.lambda().eq(Information::getUid,information.getUid());
        return informationMapper.update(information,queryWrapper)>0;
    }
}
