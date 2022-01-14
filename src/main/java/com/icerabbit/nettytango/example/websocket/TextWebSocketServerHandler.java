package com.icerabbit.nettytango.example.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @ClassName TextWebSocketServerHandler
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/14 11:59
 **/
public class TextWebSocketServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String text = ((TextWebSocketFrame)msg).text();
        ctx.channel().writeAndFlush(new TextWebSocketFrame("client:" + text));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}

