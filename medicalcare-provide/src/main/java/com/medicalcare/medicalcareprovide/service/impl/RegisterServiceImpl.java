package com.medicalcare.medicalcareprovide.service.impl;

import com.medicalcare.entity.Register;
import com.medicalcare.medicalcareprovide.mapper.RegisterMapper;
import com.medicalcare.medicalcareprovide.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterMapper registerMapper;
    @Override
    public List<Register> getAllRegister(String rid, String rname) {
        return registerMapper.getAllRegister(rid,rname);
    }
}
