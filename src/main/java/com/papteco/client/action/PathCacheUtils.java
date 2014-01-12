package com.papteco.client.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.papteco.client.ui.EnvConstant;

public class PathCacheUtils{
	public static final String PRJ_PATH = "PRJ";
	public static final String MAIL_PATH = "MAIL";
	
	public static void writePathCache(String type, String path) throws IOException {
        BufferedOutputStream buff = null;
        File tmpfile = null;
        if(type.equals(PRJ_PATH)){
        	tmpfile = new File(System.getProperty("java.io.tmpdir") + "pimsprj_" + EnvConstant.LOGIN_USER);
        	tmpfile.createNewFile();
        }else if(type.equals(MAIL_PATH)){
        	tmpfile = new File(System.getProperty("java.io.tmpdir") + "pimsmail_" + EnvConstant.LOGIN_USER);
        	tmpfile.createNewFile();
        }else{
        	System.out.println("PathCache type is invalid!");
        }
    	buff = new BufferedOutputStream(new FileOutputStream(tmpfile));
		buff.write(path.getBytes("utf-8"));
		buff.flush();
		buff.close();
	}
	
	public static String readPathCache(String type) throws IOException {
		InputStream fis = null;
		File tmpfile = null;
		if(type.equals(PRJ_PATH)){
			tmpfile = new File(System.getProperty("java.io.tmpdir") + "pimsprj_" + EnvConstant.LOGIN_USER);
			tmpfile.createNewFile();
		}else if(type.equals(MAIL_PATH)){
        	tmpfile = new File(System.getProperty("java.io.tmpdir") + "pimsmail_" + EnvConstant.LOGIN_USER);
        	tmpfile.createNewFile();
        }
		fis = new BufferedInputStream(new FileInputStream(tmpfile));
		byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        return new String(buffer, "utf-8");
	}
	
}
