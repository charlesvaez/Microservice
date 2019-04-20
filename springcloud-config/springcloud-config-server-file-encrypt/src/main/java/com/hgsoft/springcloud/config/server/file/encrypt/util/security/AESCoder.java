/**
 * 2009-10-5
 */
package com.hgsoft.springcloud.config.server.file.encrypt.util.security;

import com.sun.crypto.provider.AESKeyGenerator;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.jcajce.provider.symmetric.AES;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES安全编码组件
 * 
 */
public abstract class AESCoder {

	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	/**
	 * 加密/解密算法 / 工作模式 / 填充方式 
	 * Java 6支持PKCS5Padding填充方式 
	 * Bouncy Castle支持PKCS7Padding填充方式
	 */
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"; //AES/ECB/PKCS5Padding
	private static final String middleKey = "123456";

	private static final String ENCODING = "UTF-8";
	/**
	 * 转换密钥
	 * 
	 * @param key 二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {

		// 实例化AES密钥材料
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {

		// 还原密钥
		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, k);

		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		// 还原密钥
		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,k);

		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 生成密钥 <br>
	 * 
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
//		String key = "1234567887654321";//Key要16位的
//		SecretKey secretKey=new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
//		return secretKey.getEncoded();

		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(new SecureRandom(middleKey.getBytes()));
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] byteKey = secretKey.getEncoded();
		Key key = new SecretKeySpec(byteKey, "AES");

		return key.getEncoded();

//		// 实例化
//		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
//
//		String key = "1234567887654321";//Key要16位的
//		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//		random.setSeed(key.getBytes(ENCODING));
//
//		/*
//		 * AES 要求密钥长度为 128位、192位或 256位
//		 */
//		kg.init(128,random);
//
//		// 生成秘密密钥
//		SecretKey secretKey = kg.generateKey();
//
//		// 获得密钥的二进制编码形式
//		return secretKey.getEncoded();
	}
}
