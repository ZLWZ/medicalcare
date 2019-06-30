package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Procdetils;

import java.util.List;
import java.util.Properties;

public interface ProcdetilsService {
    boolean addProcdetils(Procdetils procdetils);

    List<Procdetils> selProcdetils(String rid);
}
