package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Apply;

import java.util.List;
import java.util.Map;

public interface ApplyService {

    boolean insertApply(Map<String, Object> map);

    List<Apply> getAllApply(String begin,String end);
}
