package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Kcdrugs;

import java.util.List;
import java.util.Map;

public interface KcdrugsService {


    List<Kcdrugs> selAllPageKcdrugs(Map<String,Object> map);


    long selKcdrugsCount(Map<String,Object> map);
}