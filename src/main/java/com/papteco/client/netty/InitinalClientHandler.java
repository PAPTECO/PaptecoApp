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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.IPItem;

public class InitinalClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(InitinalClientHandler.class.getName());

	private ClientRequestBean req = new ClientRequestBean(
			NettyConstant.INITINAL_ACTION_TYPE);

	private String localPCIP;
	private String localPCName;
	private IPItem ipItem;

	/**
	 * Creates a client-side handler.
	 * 
	 * @throws UnknownHostException
	 */
	public InitinalClientHandler(String username) throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		localPCIP = addr.getHostAddress().toString();
		localPCName = addr.getHostName().toString();
		ipItem = new IPItem(username, localPCIP, localPCName);
		req.setIpItem(ipItem);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send the first message if this handler is a client-side handler.
		ctx.writeAndFlush(req);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// Echo back the received object to the server.
		ClientRequestBean bean = (ClientRequestBean) msg;
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.info("Unexpected exception from downstream.");
		ctx.close();
	}
}
