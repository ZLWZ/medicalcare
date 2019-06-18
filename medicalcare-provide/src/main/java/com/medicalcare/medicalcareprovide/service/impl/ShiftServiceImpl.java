package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Shift;
import com.medicalcare.medicalcareprovide.mapper.ShiftMapper;
import com.medicalcare.medicalcareprovide.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {
    @Autowired
    private ShiftMapper shiftMapper;
    @Override
    public List<Shift> getAllShift() {
        return shiftMapper.getAllShift();
    }

    @Override
    public boolean updateShift(Shift shift) {
        QueryWrapper<Shift> queryWrapper = new QueryWrapper<Shift>();
        queryWrapper.lambda().eq(Shift::getSid,shift.getSid());
        return shiftMapper.update(shift,queryWrapper)>0;
    }
}
