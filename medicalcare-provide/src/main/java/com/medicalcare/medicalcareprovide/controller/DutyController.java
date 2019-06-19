package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.Shift;
import com.medicalcare.entity.User;
import com.medicalcare.medicalcareprovide.service.*;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("manager")
@RequiresPermissions(value = "/manager")
public class DutyController {
    @Autowired
    private ShiftService shiftServiceImpl;

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private DepartmentService departmentServiceImpl;

    @Autowired
    private PartiService partiServiceImpl;

    @Autowired
    private DetailsSeivice detailsSeiviceImpl;
    @RequestMapping(value = "getAllDutyInfo",method = RequestMethod.GET)
    public  Result getAllDutyInfo(@RequestParam("uid")String uid){
        List<List> list = new ArrayList<>();
        list.add(partiServiceImpl.getAllParti());
        list.add(detailsSeiviceImpl.getAllDetails());
        list.add(departmentServiceImpl.getAllDepartment());
        list.add(userServiceImpl.getUserByRid(uid));
        return new Result(ResultCode.SUCCESS,list);
    }
    @RequestMapping(value = "getAllDuty",method = RequestMethod.GET)
    public List<Shift> getAllDuty(@RequestParam("sid") String sid,@RequestParam("uname") String uname,@RequestParam("sdate") String sdate,@RequestParam("did") String did){
        List<Shift> allShift = shiftServiceImpl.getAllShift();
        List<String> uids = new ArrayList<String>();
        for (Shift uid: allShift){uids.add(uid.getUid());}
        List<User> allUser = userServiceImpl.getAllUserByuid(uids);
        for(int i = 0;i<allShift.size();i++){
            for(int j = 0;j<allUser.size();j++){
                if(allShift.get(i).getUid().equals(allUser.get(j).getUid())){
                    allShift.get(i).setUser(allUser.get(j));
                    break;
                }
            }
        }
        return getDuty(allShift,sid,uname,sdate,did);
    }
    @RequestMapping(value = "updateShift",method = RequestMethod.POST)
    public boolean updateShift(@RequestBody Shift shift){
        return shiftServiceImpl.updateShift(shift);
    }
    @RequestMapping(value = "insertShift",method = RequestMethod.POST)
    public boolean insertShift(@RequestBody Shift shift){
        return shiftServiceImpl.insertShift(shift);
    }
    @RequestMapping(value = "deleteShift",method = RequestMethod.GET)
    public boolean deleteShift(@RequestParam("sid") long sid){
        return shiftServiceImpl.deleteShift(sid);
    }
    //根据传入参数获得对应值班数据
    private List<Shift> getDuty(List<Shift> allShift,String sid,String uname,String sdate,String did)  {
        if(sid!=null&&!sid.equals("0")){
            List<Shift> u = new ArrayList<Shift>();
            for(Shift s : allShift){
                if(s.getSid()==Long.parseLong(sid)){
                    u.add(s);
                    allShift.clear();
                    allShift = u;
                    return allShift;
                }
            }
        }
        if(sdate!=null&&!sdate.equals("")){
            List<Shift> u = new ArrayList<Shift>();
            for(Shift s : allShift){
                if(s.getSdate().equals(sdate))u.add(s);
            }
            allShift.clear();
            allShift = u;
        }
        if(did!=null&&!did.equals("")){
            List<Shift> u = new ArrayList<Shift>();
            for(Shift s : allShift){
                if(s.getUser().getDepartment().getDid()==Long.parseLong(did))u.add(s);
            }
            allShift.clear();
            allShift = u;
        }
        if(uname!=null&&!uname.equals("")){
            List<Shift> u = new ArrayList<Shift>();
            for(Shift s : allShift){
                if(s.getUser().getUname().contains(uname))u.add(s);
            }
            allShift.clear();
            allShift = u;
        }
        return allShift;
    }
}
