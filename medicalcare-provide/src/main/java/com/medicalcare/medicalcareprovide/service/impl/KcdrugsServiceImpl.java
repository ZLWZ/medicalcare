package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.medicalcareprovide.mapper.KcdrugsMapper;
import com.medicalcare.medicalcareprovide.service.KcdrugsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KcdrugsServiceImpl implements KcdrugsService {

    @Resource
    private KcdrugsMapper kcdrugsMapper;

    @Override
    public List<Kcdrugs> selAllPageKcdrugs(Map<String,Object> map) {
        return kcdrugsMapper.selAllPageKcdrugs(map);
    }


    @Override
    public long selKcdrugsCount(Map<String, Object> map) {
        LambdaQueryWrapper<Kcdrugs> lambda = new QueryWrapper<Kcdrugs>().lambda();
        List<Integer> sid = (List<Integer>) map.get("sid");
        List<Integer> cid = (List<Integer>) map.get("cid");
        List<Integer> did = (List<Integer>) map.get("did");
        List<String> mkdateList = (List<String>) map.get("mkdate");
        List<String> leavedateList = (List<String>)map.get("leavedate");
        List<String> joindateList = (List<String>) map.get("joindate");
        Object name = map.get("name");
        Object ktype = map.get("ktype");
        if(sid != null && !sid.isEmpty())lambda.in(Kcdrugs::getSid,sid);
        if(cid != null && !cid.isEmpty())lambda.in(Kcdrugs::getCid,cid);
        if(did != null && !did.isEmpty())lambda.in(Kcdrugs::getDid,did);
        if(mkdateList != null && !mkdateList.isEmpty())lambda.between(Kcdrugs::getMkdate,mkdateList.get(0),mkdateList.get(1));
        if(leavedateList != null && !leavedateList.isEmpty())lambda.between(Kcdrugs::getLeavedate,leavedateList.get(0),leavedateList.get(1));
        if(joindateList != null && !joindateList.isEmpty())lambda.between(Kcdrugs::getJoindate,joindateList.get(0),joindateList.get(1));
        if(name != null && !"".equals(name)) lambda.like(Kcdrugs::getDname,name);
        if(name != null && !"".equals(ktype)) lambda.eq(Kcdrugs::getKtype,ktype);
        return kcdrugsMapper.selectCount(lambda);
    }

    @Override
    public int addKcdrugs(List<Kcdrugs> kcdrugs) {
        return kcdrugsMapper.addKcdrugs(kcdrugs);
    }


}
