package com.medicalcare.medicalcareprovide.service.impl;

import com.medicalcare.entity.Pregdetils;
import com.medicalcare.medicalcareprovide.mapper.PregdetilsMapper;
import com.medicalcare.medicalcareprovide.service.PregdetilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PregdetilsServiceImpl implements PregdetilsService {
    @Autowired
    private PregdetilsMapper pregdetilsMapper;
    @Override
    public boolean addPregdetils(Pregdetils pregdetils) {
        return pregdetilsMapper.insert(pregdetils)>0;
    }
}
