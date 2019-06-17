package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Department;

import java.util.List;

/**
 * 科室表
 */
public interface DepartmentService {
    //得到所有科室信息
    List<Department> getAllDepartment();
}
