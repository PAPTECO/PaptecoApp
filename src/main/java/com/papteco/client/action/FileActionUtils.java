package com.papteco.client.action;

import java.io.IOException;

public class FileActionUtils {
	
	public static void openFile(String file) throws IOException{
		System.out.println(System.getProperty("os.name"));
		if(System.getProperty("os.name").startsWith("Mac")){
			String[] str = {"/usr/bin/open",file};
			Runtime.getRuntime().exec(str);
		}else{
			Runtime.getRuntime().exec("cmd /C Start \" \" \"" + file + "\""); 
		}
	}

}
