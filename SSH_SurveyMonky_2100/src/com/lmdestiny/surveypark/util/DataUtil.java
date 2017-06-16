package com.lmdestiny.surveypark.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class DataUtil {
	/**
	 * 使用md5算法加密
	 */
	
	public static String md5(String src){
		char[] ch ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		StringBuffer sb = new StringBuffer();
		byte[] bytes = src.getBytes();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] targ = md.digest(bytes);
			for(byte b:targ){
				sb.append(ch[(b>>4)&0x0F]).append(ch[b&0x0F]);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 使用序列化 进行深度复制
	 */
	public static Serializable deeplyCopy(Serializable src){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.flush();
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			Serializable targ = (Serializable)ois.readObject();
			baos.close();
			oos.close();
			bais.close();
			ois.close();
			return targ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
