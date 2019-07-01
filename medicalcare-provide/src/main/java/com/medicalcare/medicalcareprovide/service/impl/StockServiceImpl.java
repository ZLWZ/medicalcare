package com.medicalcare.medicalcareprovide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicalcare.entity.Kcdrugs;
import com.medicalcare.entity.Stock;
import com.medicalcare.medicalcareprovide.mapper.StockMapper;
import com.medicalcare.medicalcareprovide.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockMapper stockMapper;

    @Override
    public int addStock(Stock stock) {
        return stockMapper.insert(stock);
    }

    @Override
    public List<Stock> selStock(Map<String,Object> map){
        return stockMapper.selStock(map);
    }

    @Override
    public long selCount(Map<String, Object> map) {
        LambdaQueryWrapper<Stock> lambda = new QueryWrapper<Stock>().lambda();
        List<Integer> sid = (List<Integer>) map.get("sid");
        List<Integer> cid = (List<Integer>) map.get("cid");
        List<Integer> did = (List<Integer>) map.get("did");
        List<String> mkdateList = (List<String>) map.get("mkdate");
        List<String> leavedateList = (List<String>)map.get("leavedate");
        List<String> joindateList = (List<String>) map.get("joindate");
        Object name = map.get("name");
        Object ktype = map.get("ktype");
        if(sid != null && !sid.isEmpty())lambda.in(Stock::getSid,sid);
        if(cid != null && !cid.isEmpty())lambda.in(Stock::getCid,cid);
        if(did != null && !did.isEmpty())lambda.in(Stock::getDid,did);
        if(mkdateList != null && !mkdateList.isEmpty())lambda.between(Stock::getMkdate,mkdateList.get(0),mkdateList.get(1));
        if(leavedateList != null && !leavedateList.isEmpty())lambda.between(Stock::getLeavedate,leavedateList.get(0),leavedateList.get(1));
        if(joindateList != null && !joindateList.isEmpty())lambda.between(Stock::getJoindate,joindateList.get(0),joindateList.get(1));
        if(name != null && !"".equals(name)) lambda.like(Stock::getSname,name);
        if(name != null && !"".equals(ktype)) lambda.eq(Stock::getStype,ktype);
        return stockMapper.selectCount(lambda);
    }
}
