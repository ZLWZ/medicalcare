package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Register;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegisterMapper extends BaseMapper<Register> {
    List<Register> getAllRegister(@Param("end") Integer end,@Param("current") Integer current,@Param("size") Integer size,@Param("rid") String rid, @Param("rname") String rname);

    boolean updateRegister(@Param("rid") String rid,@Param("rstatic") long rstatic,@Param("rprice") Double rprice);
}
