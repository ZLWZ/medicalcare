package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Pregdetils;

import java.util.List;

public interface PregdetilsService {
    boolean addPregdetils(Pregdetils pregdetils);

    List<Pregdetils> getAllPregdetils(String rid);
}
