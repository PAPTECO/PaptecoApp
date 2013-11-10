package com.papteco.client.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PathCacheUtils{
	private static File tmpfile;
	
	static {
		tmpfile = new File(System.getProperty("java.io.tmpdir") + "pathcache");
		try {
			tmpfile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeFile(String path) throws IOException {
        BufferedOutputStream buff = null;
		buff = new BufferedOutputStream(new FileOutputStream(tmpfile));
		buff.write(path.getBytes("utf-8"));
		buff.flush();
		buff.close();
	}
	
	public static String readFile() throws IOException {
		InputStream fis = new BufferedInputStream(new FileInputStream(tmpfile));
		byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        return new String(buffer, "utf-8");
	}
	
}
