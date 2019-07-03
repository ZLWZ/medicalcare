package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.medicalcare.entity.Drugs;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.medicalcareprovide.mapper.ApplyMapper;
import com.medicalcare.medicalcareprovide.mapper.DrugsMapper;
import com.medicalcare.medicalcareprovide.mapper.KcdrugsMapper;
import com.medicalcare.medicalcareprovide.service.DrugsService;
import com.medicalcare.medicalcareprovide.service.KcdrugsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class KcdrugsServiceImpl implements KcdrugsService {

    @Resource
    private KcdrugsMapper kcdrugsMapper;
    @Autowired
    private DrugsMapper drugsMapper;
    @Autowired
    private ApplyMapper applyMapper;

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
        if(ktype != null && !"".equals(ktype)) lambda.eq(Kcdrugs::getKtype,ktype);
        return kcdrugsMapper.selectCount(lambda);
    }

    @Override
    public int addKcdrugs(List<Kcdrugs> kcdrugs) {
        return kcdrugsMapper.addKcdrugs(kcdrugs);
    }

    @Override
    public int delKcdrugs() {
        return kcdrugsMapper.delete(new UpdateWrapper<Kcdrugs>().lambda().le(Kcdrugs::getNum,0));
    }

    @Override
    public boolean outYao(Kcdrugs kcdrugs) {
        List<Kcdrugs> kc = sel(kcdrugs);
        if(kc.size()==0) return  false;
        long sum = 0;
        for(Kcdrugs k : kc){sum += k.getNum();}
        if(kcdrugs.getNum()<sum){
            sum = kcdrugs.getNum();
            for(Kcdrugs k : kc){
                if(sum<=k.getNum()){
                    k.setNum(k.getNum()-sum);
                    kcdrugsMapper.updateById(k);
                    applyMapper.updateOutdate(kcdrugs.getId(),0,2,new Timestamp(new Date().getTime()));
                    if(checkDrugs(kcdrugs)){
                        drugsMapper.updateNum(sum,kcdrugs.getDname(),kcdrugs.getKtype(),kcdrugs.getSid());
                    }else{
                        k.setNum(sum);
                        insDru(k);
                    }
                    return true;
                }else{
                    sum -= k.getNum();
                    if(!checkDrugs(kcdrugs)){
                        insDru(k);
                    }
                    k.setNum(0);
                    kcdrugsMapper.updateById(k);
                }
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean chuYao(Kcdrugs kcdrugs) {
        boolean flag = false;
        List<Kcdrugs> kc = sel(kcdrugs);
        if(kc.size()==0) return  false;
        long sum = kcdrugs.getNum();
        for(Kcdrugs k : kc){
            flag = kcdrugsMapper.updateById(k)>0;
            flag = applyMapper.updateOutdate(kcdrugs.getId(),sum-k.getNum(),1,new Timestamp(new Date().getTime()));
            if(checkDrugs(kcdrugs)){
                flag = drugsMapper.updateNum(k.getNum(),kcdrugs.getDname(),kcdrugs.getKtype(),kcdrugs.getSid());
            }else{
                flag = insDru(k);
            }
            sum -= k.getNum();
            k.setNum(0);
        }
        return flag;
    }

    private List<Kcdrugs> sel(Kcdrugs kcdrugs){
        QueryWrapper<Kcdrugs> queryWrapper = new QueryWrapper<Kcdrugs>();
        queryWrapper.lambda().eq(Kcdrugs::getDname,kcdrugs.getDname());
        queryWrapper.lambda().eq(Kcdrugs::getKtype,kcdrugs.getKtype());
        queryWrapper.lambda().eq(Kcdrugs::getSid,kcdrugs.getSid());
        queryWrapper.lambda().orderByAsc(Kcdrugs::getNum);
        return kcdrugsMapper.selectList(queryWrapper);
    }

    private boolean checkDrugs (Kcdrugs kcdrugs){
        QueryWrapper<Drugs> queryWrapper = new QueryWrapper<Drugs>();
        queryWrapper.lambda().eq(Drugs::getDname,kcdrugs.getDname());
        queryWrapper.lambda().eq(Drugs::getDtype,kcdrugs.getKtype());
        queryWrapper.lambda().eq(Drugs::getSid,kcdrugs.getSid());
        Drugs drugs = drugsMapper.selectOne(queryWrapper);
        return drugs == null?false:true;
    }

    private boolean insDru(Kcdrugs k){
        Drugs drugs = new Drugs();
        drugs.setDname(k.getDname());
        drugs.setDtype(k.getKtype());
        drugs.setMoney(k.getMoney());
        drugs.setKstate(1L);
        drugs.setSid(k.getSid());
        drugs.setDid(k.getDid());
        drugs.setCid(k.getCid());
        drugs.setNum(k.getNum());
        return drugsMapper.insert(drugs)>0;
    }
}
