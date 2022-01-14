package com.icerabbit.nettytango.example.idle.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName IdleSocketServerHandler
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/14 14:57
 **/
@Slf4j
public class IdleSocketServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventDec = null;
            switch (event.state()){
                case READER_IDLE:
                    eventDec = "read time out";
                    break;
                case WRITER_IDLE:
                    eventDec = "write time out";
                    break;
                case ALL_IDLE:
                    eventDec = "read and write time out";
                    break;
                default:
            }
            log.info(ctx.channel().remoteAddress() + ":" + eventDec);
            log.info("close channel...");
            ctx.channel().close();
        }
    }
}
