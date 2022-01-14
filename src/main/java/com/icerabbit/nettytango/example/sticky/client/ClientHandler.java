package com.icerabbit.nettytango.example.sticky.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/13 14:38
 **/
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<100;i++){
            ctx.writeAndFlush("Hello World");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
