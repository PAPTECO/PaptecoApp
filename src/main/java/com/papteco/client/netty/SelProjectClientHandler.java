/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.papteco.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.papteco.client.action.JPromptWindow;
import com.papteco.client.bqueue.QueueBuilder;
import com.papteco.client.ui.EnvConstant;
import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.FileBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.beans.QueueItem;

public class SelProjectClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(SelProjectClientHandler.class.getName());

	private ClientRequestBean req = new ClientRequestBean(
			NettyConstant.SEL_PROJECT_ACTION_TYPE);
	private String prjCde;

	/**
	 * Creates a client-side handler.
	 */
	public SelProjectClientHandler(String prjCde, String reqUser) {
		req.setPrjCde(prjCde);
		req.setReqUser(reqUser);
		this.prjCde = prjCde;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send the first message if this handler is a client-side handler.
		ctx.writeAndFlush(req);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Project Search Disconnected!");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// Echo back the received object to the server.
		ClientRequestBean bean = (ClientRequestBean) msg;
		if (bean.getPrjObj() != null) {
			ProjectBean prj = (ProjectBean) bean.getPrjObj();
			this.prepareLocalFolders(prj);
			this.submitFileJobsToQueue(prj);
			JPromptWindow.showInfoMsg("Request Complete !");
		} else {
			if (bean.getReqUser() == null)
				JPromptWindow.showWarnMsg("Cannot find the user.");
			else
				JPromptWindow.showWarnMsg("Cannot find the specific project.");
		}
		ctx.close();
	}

	private void submitFileJobsToQueue(ProjectBean project) {
		for (FolderBean folder : project.getFolderTree()) {
			if (StringUtils.isNotEmpty(folder.getFolderName())
					&& folder.getFileTree() != null) {
				String folderName = folder.getFolderName();
				for (FileBean file : folder.getFileTree()) {
					File f = new File(folderName, file.getFileName());
					QueueItem q = new QueueItem("DOWNLOAD", prjCde,
							new String[] { f.getParent(), f.getName() },
							"PENDING");
					QueueBuilder.submitSingleQueue(q);
				}
			} else {
				logger.info("No Files in folder [" + folder.getFolderName()
						+ "]");
			}

		}
	}

	private void prepareLocalFolders(ProjectBean project) {
		File projectFolder = new File(EnvConstant.LCL_STORING_PATH,
				project.getProjectCde());
		if (!projectFolder.exists()) {
			projectFolder.mkdirs();
			projectFolder.setExecutable(true, false);
			projectFolder.setReadable(true, false);
			projectFolder.setWritable(true, false);
			// logger.info("Folder \"" + projectFolder.getName()
			// + "\" created!");
		} else {
			// logger.info("Folder \"" + projectFolder.getName()
			// + "\" existing already!");
		}

		for (FolderBean folder : project.getFolderTree()) {
			File sf = new File(projectFolder.getPath(), folder.getFolderName());
			if (!sf.exists()) {
				sf.mkdirs();
				sf.setExecutable(true, false);
				sf.setReadable(true, false);
				sf.setWritable(true, false);
				// logger.info("(execable, readable, writeable) - ("
				// + sf.canExecute() + ", " + sf.canRead() + ", "
				// + sf.canWrite() + ") - " + projectFolder.getPath()
				// + "/" + folder.getFolderName());
			} else {
				// logger.info("(execable, readable, writeable) - ("
				// + sf.canExecute() + ", " + sf.canRead() + ", "
				// + sf.canWrite() + ") - " + projectFolder.getPath()
				// + "/" + folder.getFolderName() + " [existing already]");
			}
		}
		logger.info("Folders creation finish.");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.info("Unexpected exception from downstream.");
		ctx.close();
	}
}
