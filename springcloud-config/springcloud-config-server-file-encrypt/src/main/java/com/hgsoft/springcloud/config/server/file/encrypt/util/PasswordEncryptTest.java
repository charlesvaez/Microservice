package com.hgsoft.springcloud.config.server.file.encrypt.util;

import com.hgsoft.springcloud.config.server.file.encrypt.util.security.AESCoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

public class PasswordEncryptTest {
    private static final String PASSWORD = "root";

    public static String initKey(){
        try {
            String key =  Hex.encodeHexString(AESCoder.initKey());
            System.out.println("初始化密码成功，密钥为："+key);

            return key;
        } catch (Exception e) {
//            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //初始化密钥
        if(StringUtils.isBlank(PasswordUtil.KEY)){
            PasswordUtil.KEY =  initKey();
        }

        //加密
        String encrypt_pwd = PasswordUtil.encode(PASSWORD);
        System.out.println("密码加密成功，加密后密文为："+encrypt_pwd);//6a660050c84ed7df9317c33560ace5f6

        //解密
        String decrypt_pwd = PasswordUtil.decode(encrypt_pwd);
        System.out.println("密码解密成功，解密后明文为："+decrypt_pwd);//
////
//
//        String key = initKey();
//        System.out.println("初始化key: "+key);
//
//        try {
//           byte[] decode_key =  Hex.decodeHex(key);
//
//            System.out.println(new String(decode_key));
//            System.out.println(Hex.encodeHexString(decode_key));
//        } catch (DecoderException e) {
//            e.printStackTrace();
//        }

//        aes();
    }

    public static void aes() {
        try {
            String middleKey = "123456";
            String password = "root";

            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom(middleKey.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
            Key key = new SecretKeySpec(byteKey, "AES");

            String keyStr = Hex.encodeHexString(key.getEncoded());
            System.out.println("密钥： "+keyStr);
            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, AESCoder.toKey(keyStr.getBytes()));
            byte[] result = cipher.doFinal(password.getBytes());
            System.out.println("加密后：" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, AESCoder.toKey(keyStr.getBytes()));
            result = cipher.doFinal(result);
            System.out.println("解密后：" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
