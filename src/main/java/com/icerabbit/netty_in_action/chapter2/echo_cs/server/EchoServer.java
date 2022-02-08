package com.icerabbit.netty_in_action.chapter2.echo_cs.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/7 15:52
 **/
@Slf4j
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            log.info("Usage: " + EchoServer.class.getSimpleName() + "<port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws Exception {
        //1. create the EventLoopGroup
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        //2. create the ServerBootstrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                //3. Specifies the use of an NIO transport Channel
                .channel(NioServerSocketChannel.class)
        //4. Sets the socket address using the specified port
                .localAddress(new InetSocketAddress(port))
        //5. Adds an EchoServerHandler to the Channel's ChannelPipeline
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });
        //6. Binds the server asynchronously;sync() waits for the bind to complete
        ChannelFuture future = serverBootstrap.bind().sync();
        //7. Gets the CloseFuture of the Channel and blocks the current thread until it's complete
        future.channel().closeFuture().sync();
        //8. Shuts down the EventLoopGroup,releasing all resources
        group.shutdownGracefully().sync();
    }
}
