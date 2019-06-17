package com.medicalcare.medicalcareprovide.service.impl;

import com.medicalcare.entity.Department;
import com.medicalcare.medicalcareprovide.mapper.DepartmentMapper;
import com.medicalcare.medicalcareprovide.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.selectList(null);
    }
}
