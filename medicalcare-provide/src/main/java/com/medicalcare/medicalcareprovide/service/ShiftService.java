package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Shift;

import java.util.List;

public interface ShiftService {
    //得到值班信息
    List<Shift> getAllShift();
    //跟新值班信息表
    boolean updateShift(Shift shift);
    //添加值班信息表
    boolean insertShift(Shift shift);
}
