package com.icerabbit.nettytango.advanced.webchat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName MyClient
 * @Description create and init client
 * @Author iceRabbit
 * @Date 2022/1/13 14:33
 **/
@Slf4j
public class Client {
    public static void main(String[] args) throws InterruptedException, IOException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());

        ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
        Channel channel = future.channel();
        log.info("client start !");

        InputStreamReader is = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(is);

        while (true){
            channel.writeAndFlush(br.readLine() + "\r\n");
        }
    }
}
