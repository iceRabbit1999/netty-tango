package com.icerabbit.nettytango.advanced.webchat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

/**
 * @ClassName ChatSocketServerHandler
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/14 14:29
 **/
@Slf4j
public class ChatSocketServerHandler extends ChannelInboundHandlerAdapter {
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress socketAddress = channel.remoteAddress();
        log.info("{}---上线",socketAddress);
        group.writeAndFlush(socketAddress + "上线\n");
        group.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress socketAddress = channel.remoteAddress();
        log.info("{}---下线",socketAddress);
        group.writeAndFlush(socketAddress + "下线,在线人数:" + group.size() + "\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        group.forEach(ch -> {
            if(channel != ch){
                ch.writeAndFlush(channel.remoteAddress() + ":" + msg + "\n");
            }else {
                ch.writeAndFlush("me:" + msg + "\n");
            }
        });


    }
}
