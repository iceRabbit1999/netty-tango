package com.icerabbit.nettytango.example.sticky.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName MyClient
 * @Description create and init client
 * @Author iceRabbit
 * @Date 2022/1/13 14:33
 **/
@Slf4j
public class Client {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());

        ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
        log.info("client start !");
        future.channel().closeFuture().sync();
    }
}
