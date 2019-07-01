package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Drugs;
import com.medicalcare.util.PageResult;

import java.util.List;
import java.util.Map;

public interface DrugsService {
    List<Drugs> getAllDrugs(String dname);

    PageResult<Drugs> getAllDrugs(Map<String,String> map);

    Drugs getDrugsByid(long did);

    boolean updateDrugs(Drugs drugs);

    boolean updateDrugState(Long id, Long kstate);
}
