package com.papteco.client.action;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.papteco.client.netty.QuartzMailBackupBuilder;
import com.papteco.client.ui.EnvConstant;

public class QuartzJob implements Job {

	public QuartzJob() {

	}

	public QuartzJob(String username) {
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			if(StringUtils.isNotEmpty(EnvConstant.LCL_MAILFILE_PATH) && new File(EnvConstant.LCL_MAILFILE_PATH).exists()){
//				System.out.println("Uploading Mail File.");
				new QuartzMailBackupBuilder().runMailBackup();
			}else{
				System.out.println("Mail-File Path is invalid!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}

}
