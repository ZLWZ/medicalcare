package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Register;

import java.util.List;

/**
 * 挂号信息记录
 */
public interface RegisterService {
    List<Register> getAllRegister(String rid, String rname);
}