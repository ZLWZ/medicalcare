package com.medicalcare.medicalcareprovide.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtils {
    /**
     * 得到用户id
     * @return
     */
    public static String getuId(){
        int uid = (int)(Math.random()*1000000);
        while (true){
            if(uid<100000) {
                uid = (int)(Math.random()*1000000);
            }else{
                return  String.valueOf(uid);
            }
        }
    }

    /**
     * 根据用户名密码加密
     * @param uname
     * @param password
     * @return
     */
    public static String getPassWord(String uname,String password){
        String algorithmName = "md5";
        Object source = password;
        Object salt = ByteSource.Util.bytes(uname);
        int hashIterations = 1024;
        SimpleHash simpleHash = new SimpleHash(algorithmName,source, salt, hashIterations);
        return simpleHash.toString();
    }

    /**
     * 得到挂号rid
     * @return
     */
    public static String getrId(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        return format.format(date);
    }

}
