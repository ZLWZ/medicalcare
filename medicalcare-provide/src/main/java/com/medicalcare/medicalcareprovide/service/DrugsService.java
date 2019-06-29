package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Drugs;

import java.util.List;

public interface DrugsService {
    List<Drugs> getAllDrugs(String dname);

    Drugs getDrugsByid(long did);
}
