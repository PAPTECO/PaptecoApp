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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.papteco.client.ui.EnvConstant;
import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.QueueItem;

public class DownFileClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(SelProjectClientHandler.class.getName());

	private ClientRequestBean req = new ClientRequestBean(
			NettyConstant.DOWN_FILE_ACTION_TYPE);

	/**
	 * Creates a client-side handler.
	 */
	public DownFileClientHandler(QueueItem qItem) {
		req.setqItem(qItem);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send the first message if this handler is a client-side handler.
		ctx.writeAndFlush(req);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Download Handler Stop!");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// Echo back the received object to the server.
		ClientRequestBean bean = (ClientRequestBean) msg;
		if (bean.getPrjObj() != null) {
			File file = new File(this.combineFolderPath(EnvConstant.LCL_STORING_PATH, bean
					.getqItem().getPrjCde()), bean.getqItem().getParam());
			System.out.println("Writing local file: " + file.getPath());
			if (!file.exists()) {
				System.out.println("File not existing: " + file.getPath());
			}
			byte[] buffer = (byte[]) bean.getPrjObj();
			BufferedOutputStream buff = null;
			buff = new BufferedOutputStream(new FileOutputStream(file));
			buff.write(buffer);
			buff.flush();
			buff.close();
		} else {
			System.out.println("Cannot find the specific file.");
		}
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.log(Level.WARNING, "Unexpected exception from downstream.",
				cause);
		ctx.close();
	}

	protected String combineFolderPath(String path1, String path2) {
		File f = new File(path1, path2);
		if (!f.exists()) {
			f.mkdirs();
			f.setExecutable(true, false);
			f.setReadable(true, false);
			f.setWritable(true, false);
		}
		return f.getPath();
	}
}
