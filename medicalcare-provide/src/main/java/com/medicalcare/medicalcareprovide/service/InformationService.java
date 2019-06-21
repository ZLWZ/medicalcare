package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Information;

import java.util.List;

public interface InformationService {
    //修改离职信息
    boolean updateInformation(Information information);
    //查询所有离职信息
    List<Information> getAllInformation();
}
