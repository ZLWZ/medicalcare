package com.medicalcare.medicalcareprovide.service.impl;

import com.medicalcare.entity.Dosage;
import com.medicalcare.medicalcareprovide.mapper.DosageMapper;
import com.medicalcare.medicalcareprovide.service.DosageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DosageServiceImpl implements DosageService {


    @Resource
    private DosageMapper dosageMapper;


    @Override
    public List<Dosage> query() {
        return dosageMapper.selectList(null);
    }
}
