package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Drugs;
import com.medicalcare.util.PageResult;

import java.util.List;

public interface DrugsService {
    List<Drugs> getAllDrugs(String dname);

    PageResult<Drugs> getAllDrugs(Drugs drugs, Integer current, Integer size);

    Drugs getDrugsByid(long did);

    boolean updateDrugs(Drugs drugs);
}
