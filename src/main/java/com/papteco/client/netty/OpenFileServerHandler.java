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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import com.papteco.client.action.FileActionUtils;
import com.papteco.client.ui.EnvConstant;
import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.QueueItem;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class OpenFileServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(OpenFileServerHandler.class.getName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ClientRequestBean bean = (ClientRequestBean) msg;
		switch (bean.getActionType()) {
		case 'P':
			if (bean.getPrjObj() != null) {
				File file = new File(EnvConstant.LCL_STORING_PATH,
						FileActionUtils.combine(bean.getqItem().getParam()));
				this.prepareFolderPath(file.getPath());
				logger.info("Writing local file: " + file.getPath());
				if (!file.exists()) {
					file.createNewFile();

					// if file already there no need to overwrite
					byte[] buffer = (byte[]) bean.getPrjObj();
					BufferedOutputStream buff = null;
					buff = new BufferedOutputStream(new FileOutputStream(file));
					buff.write(buffer);
					buff.flush();
					buff.close();
				}

			} else {
				logger.info("Cannot find the specific file.");
			}
			break;
		case 'O':
			QueueItem qItem = bean.getqItem();
			logger.info(qItem.getActionType() + ":" + qItem.getParam());
			File file = new File(EnvConstant.LCL_STORING_PATH,
					FileActionUtils.combine(qItem.getParam()));
			if (file.exists()) {
				FileActionUtils.openFile(file.getPath());
			}
			ctx.close();
			break;
		}
		ctx.writeAndFlush("FEEDBACK");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.info("Unexpected exception from downstream.");
		ctx.close();
	}

	protected void prepareFolderPath(String filepath) {
		File file = new File(filepath);
		File folder = new File(file.getParent());
		if (!folder.exists()) {
			folder.mkdirs();
			folder.setExecutable(true, false);
			folder.setReadable(true, false);
			folder.setWritable(true, false);
		}
	}
}
