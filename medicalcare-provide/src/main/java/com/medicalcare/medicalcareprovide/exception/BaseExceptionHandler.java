package com.medicalcare.medicalcareprovide.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public String error(HttpServletRequest request, HttpServletResponse response,AuthorizationException a){
        return "未授权";
    }
}
