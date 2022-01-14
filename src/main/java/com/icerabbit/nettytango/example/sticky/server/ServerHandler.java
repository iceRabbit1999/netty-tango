package com.icerabbit.nettytango.example.sticky.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName HttpServerHandler
 * @Description server handler
 * @Author iceRabbit
 * @Date 2022/1/13 11:47
 **/
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("Server received " + ++counter + "packet:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
