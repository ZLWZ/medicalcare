package com.medicalcare.medicalcareprovide.controller;

import com.medicalcare.entity.MenuRole;
import com.medicalcare.entity.Role;
import com.medicalcare.entity.UserRole;
import com.medicalcare.medicalcareprovide.service.MenuRoleService;
import com.medicalcare.medicalcareprovide.service.MenuService;
import com.medicalcare.medicalcareprovide.service.RoleService;
import com.medicalcare.medicalcareprovide.service.UserRoleService;
import com.medicalcare.medicalcareprovide.service.impl.UserRoleServiceImpl;
import com.medicalcare.util.PageResult;
import com.medicalcare.util.Result;
import com.medicalcare.util.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/jurisdiction")
@CrossOrigin

public class JurisdictionController {
    @Resource
    private RoleService roleServiceImpl;

    @Resource
    private MenuService menuServiceImpl;

    @Resource
    private MenuRoleService menuRoleServiceImpl;

    @Resource
    private UserRoleService userRoleServiceImpl;
    /**
     * 查询角色
     * @param rname
     * @param startTime
     * @param endingTime
     * @param current
     * @return
     */
    @RequiresPermissions("jurisdiction")
    @RequestMapping(value = "/selAllRole",method = RequestMethod.GET)
    public Result selAllRole(@RequestParam(value = "rname",required = false) String rname,
                             @RequestParam(value = "startTime",required = false)
                             @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ",iso = DateTimeFormat.ISO.DATE_TIME) Date startTime,
                             @RequestParam(value = "endingTime",required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ",iso = DateTimeFormat.ISO.DATE_TIME) Date endingTime,
                             @RequestParam(value = "current",required = false) Integer current){
        PageResult<Role> rolePageResult = new PageResult<>();
        rolePageResult.setTotal(roleServiceImpl.selCount(rname,startTime,endingTime))
                .setRows(roleServiceImpl.selPageRple(current == null ? 1 : current, rname, startTime, endingTime));
        return new Result(ResultCode.SUCCESS,rolePageResult);
    }

    /**
     * 按照id查询
     * @param id
     * @return
     */
    @RequiresPermissions("jurisdiction")
    @RequestMapping(value = "/selRoleId/{id}",method = RequestMethod.GET)
    public Result selRoleId(@PathVariable("id") long id){
        Role role = roleServiceImpl.selRoleId(id);
        role.setMenuList(menuServiceImpl.selMenuAndRoleList(role.getRid(),0));
        return new Result(ResultCode.SUCCESS,role);
    }

    /**
     * 查询所有的菜单
     * @return
     */
    @RequiresPermissions("jurisdiction")
    @RequestMapping(value = "/selAllMenu",method = RequestMethod.GET)
    public Result selRoleId(){
        return new Result(ResultCode.SUCCESS,menuServiceImpl.selAllMenu(0));
    }

    /**
     * 按照id查询菜单
     * @return
     */
    @RequiresPermissions("jurisdiction")
    @RequestMapping(value = "/selAllMenu/{rid}",method = RequestMethod.GET)
    public Result selUpdateRoleId(@PathVariable(value = "rid") int rid){
        return new Result(ResultCode.SUCCESS,menuServiceImpl.selHaveAll(rid));
    }
    @RequiresPermissions("jurisdiction")
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public Result addRole(@RequestBody Map map){
        Date date = new Date();
        String describe = map.get("describe").toString();
        Role role = new Role().setRname(map.get("rname").toString()).
                setDescribe(StringUtils.isEmpty(describe) ? "这个人很懒什么也没有说" : describe)
                .setCreationTime(date).setChangeTime(date);
        Object objIds = map.get("ids");
        if(objIds != null){
            List<Integer> ids = (List<Integer>) objIds;
            roleServiceImpl.addRole(role);
            for (int id : ids){
                menuRoleServiceImpl.addMenuRole(new MenuRole().setRid(role.getRid())
                        .setMid(id));
            }
        }
        return new Result(ResultCode.SUCCESS,role);
    }

    /**
     * 修改角色信息
     * @param map
     * @return
     */
    @RequiresPermissions("jurisdiction")
    @RequestMapping(value = "/updRoleMenu",method = RequestMethod.PUT)
    public Result updRoleMenu(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        int rid =(int) map.get("rid");
        if(ids != null && ids.size() > 0){
            List<MenuRole> menuRoles = menuRoleServiceImpl.selAll(rid);
            List<Integer> menuIds = new ArrayList<Integer>();
            for(MenuRole menuRole : menuRoles) menuIds.add((int)menuRole.getMid());
            for(Integer mid : ids)if(menuIds.indexOf(mid) <= -1)menuRoleServiceImpl.addMenuRole(new MenuRole().setRid(rid).setMid(mid));
            menuRoleServiceImpl.delMenuRole(ids,rid);
        }else{
            menuRoleServiceImpl.delAll(rid);
        }
        roleServiceImpl.updateRoleId(new Role().setChangeTime(new Date()).setDescribe(map.get("describe").toString())
                .setRname(map.get("rname").toString()).setRid((int) map.get("rid")));
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除角色
     * @param rid
     * @return
     */
    @RequiresPermissions("jurisdiction")
    @RequestMapping(value = "/deleteRole/{rid}",method = RequestMethod.DELETE)
    public Result deleteRole(@PathVariable(value = "rid") long rid){
        List<UserRole> userRoles = userRoleServiceImpl.selAllRid(rid);
        if(userRoles == null || userRoles.size() == 0){
            menuRoleServiceImpl.delAll(rid);
            roleServiceImpl.deleteRole(rid);
            return new Result(ResultCode.SUCCESS,"删除成功");
        }else{
            return new Result(ResultCode.FAIL,"删除失败，该角色正在被使用");
        }
    }
}
