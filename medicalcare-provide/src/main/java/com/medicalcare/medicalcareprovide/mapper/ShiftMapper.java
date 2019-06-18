package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Shift;

import java.util.List;

public interface ShiftMapper extends BaseMapper<Shift> {
    //得到所有值班信息
    List<Shift> getAllShift();
}
