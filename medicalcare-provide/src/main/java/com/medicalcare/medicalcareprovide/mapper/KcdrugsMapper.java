package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Kcdrugs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface KcdrugsMapper extends BaseMapper<Kcdrugs> {

    List<Kcdrugs> selAllPageKcdrugs(@Param("map") Map<String,Object> map);

    int addKcdrugs(@Param("kcdrugs") List<Kcdrugs> kcdrugs);
}
