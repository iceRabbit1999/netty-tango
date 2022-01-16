package com.icerabbit.nettytango.advanced.heartbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @ClassName HttpChannelInitializer
 * @Description channel initial
 * @Author iceRabbit
 * @Date 2022/1/13 11:43
 **/
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private Bootstrap bootstrap;

    public ClientChannelInitializer(Bootstrap bootstrap){
        this.bootstrap = bootstrap;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new HeartBeatHandler(bootstrap));
    }
}
