package com.hgsoft.springcloud.config.server.file.encrypt.util.security;

import org.apache.commons.codec.binary.Base64;

public abstract class Base64Coder {

	/**
	 * 字符编码
	 */
	public final static String ENCODING = "UTF-8";

	/**
	 * Base64编码
	 * 
	 * @param data
	 *            待编码数据
	 * @return String 编码数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {

		// 执行编码
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));

		return new String(b, ENCODING);
	}

	/**
	 * Base64安全编码<br>
	 * 遵循RFC 2045实现
	 * 
	 * @param data
	 *            待编码数据
	 * @return String 编码数据
	 * 
	 * @throws Exception
	 */
	public static String encodeSafe(String data) throws Exception {

		// 执行编码
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);

		return new String(b, ENCODING);
	}

	/**
	 * Base64解码
	 * 
	 * @param data
	 *            待解码数据
	 * @return String 解码数据
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {

		// 执行解码
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));

		return new String(b, ENCODING);
	}

	public static void main(String[] args) throws Exception {
		String inputStr = "Java加密与解密的艺术";

		System.err.println("原文:\t" + inputStr);

		// 进行Base64编码
		String code = Base64Coder.encodeSafe(inputStr);
		// String code = Base64Coder.encode(inputStr);

		System.err.println("编码后:\t" + code);

		// 进行Base64解码
		String outputStr = Base64Coder.decode(code);

		System.err.println("解码后:\t" + outputStr);

	}

}
