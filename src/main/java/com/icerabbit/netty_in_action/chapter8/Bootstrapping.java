package com.icerabbit.netty_in_action.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/10 16:26
 **/
@Slf4j
public class Bootstrapping {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        log.info("Received data");
                    }
                });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    log.info("Connection established");
                }else{
                    log.info("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
