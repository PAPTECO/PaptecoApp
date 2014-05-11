package com.papteco.client.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.log4j.Logger;

public class FileActionUtils {

	protected static final Logger log = Logger.getLogger(FileActionUtils.class
			.getName());

	public static void openFile(String file) throws IOException {
		log.info(System.getProperty("os.name"));
		if (System.getProperty("os.name").startsWith("Mac")) {
			String[] str = { "/usr/bin/open", file };
			Runtime.getRuntime().exec(str);
		} else {
			Runtime.getRuntime().exec("cmd /C Start \" \" \"" + file + "\"");
		}
	}

	public static String combine(String path1, String path2) {
		File file1 = new File(path1);
		File file2 = new File(file1, path2);
		return file2.getPath();
	}

	public static String combine(String[] paths) {

		File f = null;
		for (String path : paths) {
			if (f == null)
				f = new File(path);
			else
				f = new File(f, path);
		}
		return f == null ? "" : f.toString();
	}

	public static String[] getLastModifiedFile(String dirPath) {
		String[] result = null;
		File dir = new File(dirPath);
		if (!dir.exists())
			dir.mkdir();
		File[] files = dir.listFiles();
		if (files != null && files.length > 0) {
			result = new String[3];
			Arrays.sort(files, new LastModifiedFileComparator());
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile()) {
					result[0] = file.getPath();
					result[1] = String.valueOf(file.lastModified());
					result[2] = file.getName().contains(".") ? file.getName()
							.substring(file.getName().lastIndexOf(".")) : "";
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
