package com.icerabbit.nettytango.example.decoder.line.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName HttpServer
 * @Description create and initial ServerBootStrap
 * @Author iceRabbit
 * @Date 2022/1/13 11:38
 **/
@Slf4j
public class Server {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup,childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer());

        ChannelFuture future = bootstrap.bind(8080).sync();
        log.info("server start !");
        future.channel().closeFuture().sync();

    }
}
