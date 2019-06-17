package com.medicalcare.medicalcareprovide.shiro_dev;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.boot.autoconfigure.session.SessionProperties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class MySessionManager extends DefaultWebSessionManager {

    /**
     * 请求头信息中具有Authoriztion sessionid
     * 指定sessionid的获取方式
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader("Authoriztion");
        System.out.println("请求头"+id);
        if(StringUtils.isEmpty(id)){
            return super.getSessionId(request,response);
        }else{
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");//从什么地方获取的
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);//sessionid是什么
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);//需不需要进行验证
            return id;
        }
    }
}
