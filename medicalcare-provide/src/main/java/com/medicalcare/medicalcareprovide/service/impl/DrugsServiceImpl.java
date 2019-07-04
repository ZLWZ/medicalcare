package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Drugs;
import com.medicalcare.medicalcareprovide.mapper.DrugsMapper;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.util.PageResult;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class DrugsServiceImpl implements DrugsService {
    @Autowired
    private DrugsMapper drugsMapper;
    @Override
    public List<Drugs> getAllDrugs(String dname) {
        if(StringUtils.isEmpty(dname)){
            return drugsMapper.selectList(null);
        }
        QueryWrapper<Drugs> queryWrapper = new QueryWrapper<Drugs>();
        queryWrapper.lambda().like(Drugs::getDname,dname);
        return drugsMapper.selectList(queryWrapper);
    }

    @Override
    public PageResult<Drugs> getAllDrugs(Map<String,String> map) {
        String dname = (String)map.get("dname");
        Long dtype = Long.parseLong(map.get("dtype"));
        Long cid =  Long.parseLong(map.get("cid"));
        Long did =  Long.parseLong(map.get("did"));
        Long sid =  Long.parseLong(map.get("sid"));
        Integer current =  Integer.parseInt(map.get("current"));
        Integer size =  Integer.parseInt(map.get("size"));
        PageResult<Drugs> result = new PageResult<Drugs>();
        result.setRows(drugsMapper.getAllDrugs(dname,dtype,cid,did,sid,(current-1)*size,size));
        QueryWrapper<Drugs> queryWrapper = new QueryWrapper<Drugs>();
        if(!StringUtils.isEmpty(dname))queryWrapper.lambda().like(Drugs::getDname,dname);
        if(dtype!=0)queryWrapper.lambda().eq(Drugs::getDtype,dtype);
        if(cid!= 0)queryWrapper.lambda().eq(Drugs::getCid,cid);
        if(did!= 0)queryWrapper.lambda().eq(Drugs::getDid,did);
        if(sid!= 0)queryWrapper.lambda().eq(Drugs::getSid,sid);
        result.setTotal(drugsMapper.selectCount(queryWrapper).longValue());
        return result;
    }

    @Override
    public Drugs getDrugsByid(long did) {
        return drugsMapper.selectById(did);
    }

    @Override
    public boolean updateDrugs(Drugs drugs) {
        return drugsMapper.updateById(drugs)>0;
    }

    @Override
    public boolean updateDrugState(Long id, Long kstate) {
        return drugsMapper.updateDrugState(id,kstate);
    }

    @Override
    public List<Drugs> getDrugDefi() {
        QueryWrapper<Drugs> queryWrapper = new QueryWrapper<Drugs>();
        queryWrapper.lambda().lt(Drugs::getNum,10);
        return drugsMapper.selectList(queryWrapper);
    }

    @Override
    public List<Drugs> getTongJi() {
        return drugsMapper.getTongJi();
    }
}
