package com.hgsoft.springcloud.config.server.file.encrypt.util;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

public class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {
    public static final String ENCODED_PASSWORD_HINT = "{cipher}";

    //自定义解密方法
    @Override
    public String resolvePropertyValue(String s) {
        if (null != s && s.startsWith(ENCODED_PASSWORD_HINT)) {
            return PasswordUtil.decode(s.substring(ENCODED_PASSWORD_HINT.length()));
        }
        return s;
    }
}