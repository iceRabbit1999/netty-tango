package com.icerabbit.netty_in_action.chapter13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/14 14:58
 **/
public class LogEventBroadcaster {
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new io.netty.bootstrap.Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));

        this.file = file;

    }

    public void run() throws Exception {
        Channel ch = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        for (; ; ) {
            long len = file.length();
            if(len < pointer){
                //file was reset
                pointer = len;
            }else if(len > pointer){
                //Content was added
                RandomAccessFile raf = new RandomAccessFile(file,"r");
                raf.seek(pointer);
                String line;
                while((line = raf.readLine()) != null){
                    ch.writeAndFlush(new LogEvent(null, -1, file.getAbsolutePath(),line));
                }

                pointer = raf.getFilePointer();
                raf.close();
            }
            Thread.sleep(1000);
        }
    }

    public void stop(){
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(new InetSocketAddress("255.255.255.255",Integer.parseInt(args[0])),new File(args[1]));
        broadcaster.run();
    }

}
