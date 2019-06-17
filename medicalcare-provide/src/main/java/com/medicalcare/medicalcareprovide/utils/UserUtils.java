package com.medicalcare.medicalcareprovide.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class UserUtils {
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
    public static String getPassWord(String uname){
        String algorithmName = "md5";
        Object source = "123456";
        Object salt = ByteSource.Util.bytes(uname);
        int hashIterations = 1024;
        SimpleHash simpleHash = new SimpleHash(algorithmName,source, salt, hashIterations);
        return simpleHash.toString();
    }
}
