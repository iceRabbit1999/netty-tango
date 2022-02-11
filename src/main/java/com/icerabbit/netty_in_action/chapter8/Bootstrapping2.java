package com.icerabbit.netty_in_action.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @ClassName
 * @Description bootstrap a client Channel from a ServerChannel
 * @Author iceRabbit
 * @Date 2022/2/10 16:53
 **/
@Slf4j
public class Bootstrapping2 {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //Set the EventLoopGroups that provide EventLoops for processing Channel events
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                        log.info("Received data");
                                    }
                                });

                        bootstrap.group(ctx.channel().eventLoop());
                        ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 80));
                    }
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        //Do something with the data
                    }
                });

        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    log.info("Server bound");
                }else{
                    log.info("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
