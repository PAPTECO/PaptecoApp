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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.papteco.client.action.FileActionUtils;
import com.papteco.client.ui.EnvConstant;
import com.papteco.web.beans.ClientRequestBean;

public class QuartzMailBackupClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(QuartzMailBackupClientHandler.class.getName());

	private ClientRequestBean req = new ClientRequestBean(
			NettyConstant.MAILBKP_INI_ACTION_TYPE);
	
	/**
	 * Creates a client-side handler.
	 * @throws UnknownHostException 
	 */
	public QuartzMailBackupClientHandler() throws UnknownHostException {
		req.setReqUser(EnvConstant.LOGIN_USER);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send the first message if this handler is a client-side handler.
		ctx.writeAndFlush(req);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// Echo back the received object to the server.
		ClientRequestBean bean = (ClientRequestBean) msg;
		bean.setActionType(NettyConstant.MAILBKP_UP_ACTION_TYPE);
		String[] lastMailFile = FileActionUtils.getLastModifiedFile(EnvConstant.LCL_MAILFILE_PATH);
		if(lastMailFile != null && StringUtils.isNotEmpty(lastMailFile[1])){
			if(bean.getTimestamp() != null && 
					Long.valueOf(lastMailFile[1]) <= Long.valueOf(bean.getTimestamp())){
//				System.out.println("No Mail-File's update.");
			}else{
				File file = new File(lastMailFile[0]);
				InputStream fis = new BufferedInputStream(new FileInputStream(file));
				byte[] buffer = new byte[fis.available()];
		        fis.read(buffer);
		        fis.close();
		        bean.setPrjObj(buffer);
		        bean.setTimestamp(lastMailFile[1]);
		        bean.setMailfileSuffix(lastMailFile[2]);
			}
		}
        ctx.write(bean);
	}

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.log(Level.WARNING, "Unexpected exception from downstream.",
				cause);
		ctx.close();
	}
}
