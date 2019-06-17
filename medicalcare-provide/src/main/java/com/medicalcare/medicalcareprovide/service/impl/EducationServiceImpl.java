package com.medicalcare.medicalcareprovide.service.impl;

import com.medicalcare.entity.Education;
import com.medicalcare.medicalcareprovide.mapper.EducationMapper;
import com.medicalcare.medicalcareprovide.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EducationServiceImpl implements EducationService {
    @Autowired
    private EducationMapper educationMapper;
    @Override
    public List<Education> getAllEducation() {
        return educationMapper.selectList(null);
    }
}
