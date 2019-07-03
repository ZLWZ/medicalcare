package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Apply;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;

public interface ApplyMapper extends BaseMapper<Apply> {
    boolean updateOutdate(@Param("aid") long aid,@Param("num") long num,@Param("state") long state, @Param("outdate") Timestamp outdate);
}
