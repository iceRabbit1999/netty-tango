package com.icerabbit.nettytango.example.decoder.line.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @ClassName HttpChannelInitializer
 * @Description channel initial
 * @Author iceRabbit
 * @Date 2022/1/13 11:43
 **/
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //客户端只需要发送数据,只需要编码器
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ClientHandler());
    }
}
