package com.icerabbit.netty_in_action.chapter4.jdk;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/8 15:09
 **/
@Slf4j
public class PlainNioServer {
    public void serve(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket ssocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        //Bind the server to the selected port
        ssocket.bind(address);
        //Open the selector for handling channels
        Selector selector = Selector.open();
        //Registers the ServerSocket with the Selector to accept connections
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        for (; ; ) {
            //Wait for new events to process; blocks until the next incoming event
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        //Obtains all SelectionKey instances tha received events
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            //Check if the event is a new connection ready to be accepted
            if (key.isAcceptable()) {
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                SocketChannel client = server.accept();
                client.configureBlocking(false);
                //Accepts client and registers it with the selector
                client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                log.info("Accepted connection from " + client);
            }
            //Check if the socket is ready for writing data
            if (key.isWritable()) {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                while (buffer.hasRemaining()) {
                    //Write data to the connected client
                    if (client.write(buffer) == 0) {
                        break;
                    }
                }
                //Close the connection
                client.close();
            }
        }
    }
}
