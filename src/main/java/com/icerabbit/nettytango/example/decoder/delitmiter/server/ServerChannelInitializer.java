package com.icerabbit.nettytango.example.decoder.delitmiter.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @ClassName HttpChannelInitializer
 * @Description channel initial
 * @Author iceRabbit
 * @Date 2022/1/13 11:43
 **/
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        ByteBuf delimiter = Unpooled.copiedBuffer(":".getBytes());
        pipeline.addLast(new DelimiterBasedFrameDecoder(6144,delimiter));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ServerHandler());
    }
}
