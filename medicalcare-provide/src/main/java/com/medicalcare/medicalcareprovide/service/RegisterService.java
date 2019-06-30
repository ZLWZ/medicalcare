package com.medicalcare.medicalcareprovide.service;

import com.medicalcare.entity.Register;
import com.medicalcare.util.PageResult;

import java.util.List;

/**
 * 挂号信息记录
 */
public interface RegisterService {
    PageResult<Register> getAllRegister(Integer end,Integer current,Integer size,String rid, String rname);

    Register getRegister(String rid);

    boolean updateRegister(Register register);

    boolean updateRstatic(String rid,Integer rstatic,Double rprice);

    boolean addRegister(Register register);

    boolean updatePstate(String rid,Double rprice,long pstate);
}
