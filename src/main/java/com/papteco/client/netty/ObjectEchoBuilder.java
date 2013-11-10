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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import com.papteco.web.beans.QueueItem;

public class ObjectEchoBuilder {

	private final String host = "localhost";
	private final int port = 8081;
	private String inPrjCde;
	private QueueItem qItem = new QueueItem();

	public ObjectEchoBuilder(){
	}
	
	public ObjectEchoBuilder(String inPrjCde) {
		this.inPrjCde = inPrjCde;
	}

	public ObjectEchoBuilder(QueueItem qItem) {
		this.qItem = qItem;
	}

	public void runInitinal() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new ObjectEncoder(),
									new ObjectDecoder(ClassResolvers
											.cacheDisabled(null)),
									new InitinalClientHandler("conygychen"));
						}
					});
			b.connect(host, port).sync().channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public void runSelProjectEcho() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new ObjectEncoder(),
									new ObjectDecoder(ClassResolvers
											.cacheDisabled(null)),
									new SelProjectClientHandler(inPrjCde));
						}
					});
			b.connect(host, port).sync().channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	public void runDownFileEcho() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new ObjectEncoder(),
									new ObjectDecoder(ClassResolvers
											.cacheDisabled(null)),
									new DownFileClientHandler(qItem));
						}
					});
			b.connect(host, port).sync().channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
