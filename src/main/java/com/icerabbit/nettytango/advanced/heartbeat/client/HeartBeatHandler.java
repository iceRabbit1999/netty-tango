package com.icerabbit.nettytango.advanced.heartbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName HeartBeatHandler
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/14 15:43
 **/
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private GenericFutureListener listener;
    private ScheduledFuture<?> scheduledFuture;
    private Bootstrap bootstrap;

    public HeartBeatHandler(Bootstrap bootstrap){
        this.bootstrap = bootstrap;
    }
    public HeartBeatHandler(){
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        randomSend(ctx.channel());
    }

    private void randomSend(Channel channel) {
        int heartBeatInterval = new Random().nextInt(7) + 1;
        log.info("{}后发送下一次心跳", heartBeatInterval);

        // 为eventLoop添加异步定时任务——heartBeatInterval秒后发送下一次心跳
        scheduledFuture = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                log.info("sent heartBeat to server");
                channel.writeAndFlush("ping");
            }else {
                log.info("disconnected from server");
            }
        },heartBeatInterval, TimeUnit.SECONDS);

        listener = (future -> {
            randomSend(channel);
        });

        scheduledFuture.addListener(listener);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        scheduledFuture.removeListener(listener);
        log.info("Reconnecting...");
        bootstrap.connect("localhost",8080).sync();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
