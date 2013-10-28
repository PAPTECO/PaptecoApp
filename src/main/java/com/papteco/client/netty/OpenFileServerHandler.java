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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.papteco.client.action.FileActionUtils;
import com.papteco.client.ui.EnvConstant;
import com.papteco.web.beans.QueueItem;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class OpenFileServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(
            OpenFileServerHandler.class.getName());

    @Override
    public void channelRead(
            ChannelHandlerContext ctx, Object msg) throws Exception {
        // Echo back the received object to the client.
    	QueueItem qItem = (QueueItem) msg;
    	System.out.println(qItem.getActionType()+":"+qItem.getParam());
    	File file = new File(EnvConstant.LCL_STORING_PATH,qItem.getParam());
    	if(file.exists()){
    		FileActionUtils.openFile(file.getPath());
    	}
    	ctx.close();
//        ctx.write(qItem);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
