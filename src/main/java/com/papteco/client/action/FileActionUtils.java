package com.papteco.client.action;

import java.io.IOException;

public class FileActionUtils {
	
	public static void openFile(String file) throws IOException{
		Runtime.getRuntime().exec("cmd /C Start " + file); 
	}

}
