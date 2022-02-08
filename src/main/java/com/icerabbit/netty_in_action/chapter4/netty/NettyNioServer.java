package com.icerabbit.netty_in_action.chapter4.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/8 15:47
 **/
@Slf4j
public class NettyNioServer {
    public void serve(int port)throws Exception{
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", StandardCharsets.UTF_8));
        //Use NioEventLoopGroup for non-blocking mode
        EventLoopGroup group = new NioEventLoopGroup();
        //Create ServerBootstrap
        ServerBootstrap b = new ServerBootstrap();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                //Specifies ChannelInitializer to be called for each accepted connection
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //Add ChannelInboundHandlerAdapter to receive events and process them
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            //Write message to client and add ChannelFutureListener to close the connection once the message is written
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
