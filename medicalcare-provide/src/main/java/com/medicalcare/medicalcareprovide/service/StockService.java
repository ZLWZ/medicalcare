package com.medicalcare.medicalcareprovide.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.entity.Stock;

import java.util.List;
import java.util.Map;

public interface StockService {

    int addStock(Stock stock);

    List<Stock> selStock(Map<String,Object> map);

    long selCount(Map<String, Object> map);
}
