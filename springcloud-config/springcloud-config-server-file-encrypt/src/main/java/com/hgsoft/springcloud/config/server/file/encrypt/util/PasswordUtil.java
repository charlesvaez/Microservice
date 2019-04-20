package com.hgsoft.springcloud.config.server.file.encrypt.util;

import com.hgsoft.springcloud.config.server.file.encrypt.util.security.AESCoder;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;

public class PasswordUtil {
    public static String KEY = "6bb4837eb74329105ee4568dda7dc67e";

    /**
     * 密码加密
     * @param password 明文密码
     * @return 16进制密文
     */
    public static String encode(String password){
        if(StringUtils.isBlank(password)){
            throw new RuntimeException("传入密码为空!");
        }

        String encrypt_pwd = null;
        try {
            encrypt_pwd = Hex.encodeHexString(AESCoder.encrypt(password.getBytes(),KEY.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("密码加密失败!",e);
        }

        return encrypt_pwd;
    }

    /**
     * 密码解密
     * @param password 密文密码
     * @return
     */
    public static String decode(String password){

        if(StringUtils.isBlank(password)){
            throw new RuntimeException("传入密码为空!");
        }

        String decrypt_pwd = null;
        try {
            decrypt_pwd = new String(AESCoder.decrypt(Hex.decodeHex(password),KEY.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("密码解密失败!",e);
        }
        return decrypt_pwd;
    }
}
