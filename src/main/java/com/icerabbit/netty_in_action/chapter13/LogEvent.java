package com.icerabbit.netty_in_action.chapter13;

import lombok.Data;

import java.net.InetSocketAddress;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/14 14:21
 **/
@Data
public class LogEvent {
    public static final byte SEPARATOR = ':';
    private InetSocketAddress source;
    private String logfile;
    private String msg;
    private long received;

    public LogEvent(){}

    public LogEvent(String logfile, String msg) {
        this.logfile = logfile;
        this.msg = msg;
    }

    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.received = received;
        this.logfile = logfile;
        this.msg = msg;
    }
}
