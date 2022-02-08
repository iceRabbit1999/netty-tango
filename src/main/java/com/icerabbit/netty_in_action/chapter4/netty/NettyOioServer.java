package com.icerabbit.netty_in_action.chapter4.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/8 15:38
 **/
@Slf4j
public class NettyOioServer {
    public void serve(int port) throws Exception{
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", StandardCharsets.UTF_8));
        EventLoopGroup group = new OioEventLoopGroup();
        //Create a ServerBootstrap
        ServerBootstrap b = new ServerBootstrap();
        b.group(group)
                //Use OioEventLoopGroup to allow blocking mode
                .channel(OioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                //Specifies ChannelInitializer that will be called for each accepted connection
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //Add a ChannelInboundHandlerAdapter to intercept handler events
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ctx.writeAndFlush(buf.duplicate())
                                        .addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        //Bind server to accept connections
        ChannelFuture f = b.bind().sync();
        f.channel().closeFuture().sync();
        //Release all resources
        group.shutdownGracefully().sync();
    }
}
