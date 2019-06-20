package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Register;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegisterMapper extends BaseMapper<Register> {
    List<Register> getAllRegister(@Param("rid") String rid, @Param("rname") String rname);

    boolean updateRegister(@Param("rid") String rid,@Param("rstatic") long rstatic);
}
