package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Stock;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StockMapper extends BaseMapper<Stock> {


    List<Stock> selStock(@Param("map")Map<String,Object> map);
}
