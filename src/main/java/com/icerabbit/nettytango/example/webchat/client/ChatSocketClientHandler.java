package com.icerabbit.nettytango.example.webchat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ChatSocketClientHandler
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/14 14:44
 **/
@Slf4j
public class ChatSocketClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        log.info(msg);
    }
}
