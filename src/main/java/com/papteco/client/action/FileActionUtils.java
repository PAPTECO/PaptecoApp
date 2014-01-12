package com.papteco.client.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class FileActionUtils {

	public static void openFile(String file) throws IOException {
		System.out.println(System.getProperty("os.name"));
		if (System.getProperty("os.name").startsWith("Mac")) {
			String[] str = { "/usr/bin/open", file };
			Runtime.getRuntime().exec(str);
		} else {
			Runtime.getRuntime().exec("cmd /C Start \" \" \"" + file + "\"");
		}
	}

	public static String[] getLastModifiedFile(String dirPath) {
		String[] result= null;
		File dir = new File(dirPath);
		if(!dir.exists())
			dir.mkdir();
		File[] files = dir.listFiles();
		if(files != null && files.length > 0){
			result = new String[3];
			Arrays.sort(files, new LastModifiedFileComparator());
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if(file.isFile()){
					result[0] = file.getPath();
					result[1] = String.valueOf(file.lastModified());
					result[2] = file.getName().contains(".")?file.getName().substring(file.getName().lastIndexOf(".")):"";
					break;
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		FileActionUtils.getLastModifiedFile("C:\\Users\\Cony\\git\\papteco");
	}
}

class LastModifiedFileComparator implements Comparator {
	public int compare(Object object1, Object object2) {
		File file1 = (File) object1;
		File file2 = (File) object2;
		long result = file1.lastModified() - file2.lastModified();
		if (result < 0) {
			return 1;
		} else if (result > 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
