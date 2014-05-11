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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.papteco.client.action.FileActionUtils;
import com.papteco.client.ui.EnvConstant;
import com.papteco.web.beans.ClientRequestBean;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ReleaseFileServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(ReleaseFileServerHandler.class.getName());

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ClientRequestBean bean = (ClientRequestBean) msg;
		switch (bean.getActionType()) {
		case 'R':
			if (bean.getqItem() != null) {
				File file = new File(EnvConstant.LCL_STORING_PATH,
						FileActionUtils.combine(bean.getqItem().getParam()));
				if (file.exists()) {
					InputStream fis = new BufferedInputStream(
							new FileInputStream(file));
					byte[] buffer = new byte[fis.available()];
					fis.read(buffer);
					fis.close();
					bean.setPrjObj(buffer);
				}
			} else {
				logger.info("Cannot find the specific file.");
			}
			break;
		}
		ctx.writeAndFlush(bean);
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
}
