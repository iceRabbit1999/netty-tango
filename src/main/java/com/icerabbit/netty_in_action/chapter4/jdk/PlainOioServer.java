package com.icerabbit.netty_in_action.chapter4.jdk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/8 14:54
 **/
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
@Slf4j
public class PlainOioServer {

    public void serve(int port) throws Exception{
        //Bind the server to specified port
        final ServerSocket socket = new ServerSocket(port);
        for(;;){
            //Accept a connection
            final Socket clientSocket = socket.accept();
            log.info("Accepted connection from " + clientSocket);

            //Create a new thread to handle the connection
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    //Write message to the connected client
                    OutputStream out = clientSocket.getOutputStream();
                    out.write("Hi!\r\n".getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    //Close the connection
                    clientSocket.close();
                }
            }).start();


        }
    }
}
