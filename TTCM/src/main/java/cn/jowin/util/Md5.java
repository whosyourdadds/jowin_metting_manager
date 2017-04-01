package cn.jowin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

//import org.apache.tomcat.util.codec.binary.Base64;

import org.apache.commons.codec.binary.Base64;

//import org.apache.commons.codec.binary.Base64;

public class Md5 {
	public static final String salt = "cn.tedu.note";
	
	public static final String salt2 = "cn.jowin.ttcm";
	public static String saltMd5(String str){
		try {
			byte[] data = str.getBytes("utf-8");
			MessageDigest md=
				MessageDigest.getInstance("md5");
			md.update(data);
			md.update(salt.getBytes("utf-8"));
			byte[] md5=md.digest();
			String code=
				Base64.encodeBase64String(md5);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String md5(String str){
		try {
			byte[] data = str.getBytes("utf-8");
			MessageDigest md=
				MessageDigest.getInstance("md5");
			md.update(data);
			byte[] md5=md.digest();
			String code=
				Base64.encodeBase64String(md5);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
