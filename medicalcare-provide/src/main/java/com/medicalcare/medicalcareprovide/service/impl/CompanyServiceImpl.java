package com.medicalcare.medicalcareprovide.service.impl;

import com.medicalcare.entity.Company;
import com.medicalcare.medicalcareprovide.mapper.CompanyMapper;
import com.medicalcare.medicalcareprovide.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public List<Company> selAllCompany() {
        return companyMapper.selectList(null);
    }
}
