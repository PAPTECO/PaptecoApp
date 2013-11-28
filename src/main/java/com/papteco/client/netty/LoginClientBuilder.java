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
import io.netty.handler.codec.serialization.ObjectEncoder;

public class LoginClientBuilder extends BasicBuilder {

	private String username;
	private String password;

	public LoginClientBuilder(){
	}
	
	public LoginClientBuilder(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void validateUser() throws Exception {
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
									new NewObjectDecoder(ClassResolvers
											.cacheDisabled(null)),
									new LoginClientHandler(username, password));
						}
					});
			b.connect(envsetting.getProperty("pims_ip"), PortTranslater(envsetting.getProperty("login_sym_port"))).sync().channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
	
}
