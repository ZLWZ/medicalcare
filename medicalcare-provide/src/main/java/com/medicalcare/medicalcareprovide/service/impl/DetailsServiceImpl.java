package com.medicalcare.medicalcareprovide.service.impl;

import com.medicalcare.entity.Details;
import com.medicalcare.medicalcareprovide.mapper.DetailsMapper;
import com.medicalcare.medicalcareprovide.service.DetailsSeivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsServiceImpl implements DetailsSeivice {
    @Autowired
    private DetailsMapper detailsMapper;
    @Override
    public List<Details> getAllDetails() {
        return detailsMapper.selectList(null);
    }
}
