package com.hujunchina.client.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 7:27 下午
 * @Version 1.0
 * 安全相关
 */
public class SecuritySorts {
    public static void main(String[] args) {

    }

    public static String MD5(String msg) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] ret = md5.digest(msg.getBytes());
        return ret.toString();
    }

    //sha = secure hash algorithm 安全哈希算法
    public static String SHA(String msg) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("sha1");
        byte[] ret = sha.digest(msg.getBytes());
        return ret.toString();
    }
}
