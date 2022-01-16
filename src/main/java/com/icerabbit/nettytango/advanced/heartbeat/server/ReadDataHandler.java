package com.icerabbit.nettytango.advanced.heartbeat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ReadDataHandler
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/14 15:35
 **/
@Slf4j
public class ReadDataHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("data from client:{}",msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent) evt).state();
            if(state == IdleState.READER_IDLE){
                ctx.disconnect();
            }else {
                super.userEventTriggered(ctx,evt);
            }
        }
    }
}
