package com.medicalcare.medicalcareprovide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicalcare.entity.Drugs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrugsMapper extends BaseMapper<Drugs> {
    List<Drugs> getAllDrugs(@Param("dname") String dname,@Param("dtype") Long dtype,@Param("cid") Long cid,
                @Param("did") Long did,@Param("sid") Long sid,@Param("current") Integer current,@Param("size") Integer size);

    boolean updateDrugState(@Param("id")Long id,@Param("kstate") Long kstate);

    boolean updateNum(@Param("num")long num,@Param("dname")String dname,@Param("dtype") Long dtype,@Param("sid") Long sid);

    List<Drugs> getTongJi();
}
