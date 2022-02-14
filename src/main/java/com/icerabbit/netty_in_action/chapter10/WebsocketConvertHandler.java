package com.icerabbit.netty_in_action.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/14 10:26
 **/
public class WebsocketConvertHandler extends MessageToMessageCodec<WebSocketFrame,WebsocketConvertHandler.MyWebSocketFrame> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MyWebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.getData().duplicate().retain();
        switch (msg.getType()){
            case BINARY:
                out.add(new BinaryWebSocketFrame(payload));
                break;
            case TEXT:
                out.add(new TextWebSocketFrame(payload));
                break;
            case CLOSE:
                out.add(new CloseWebSocketFrame(true,0,payload));
                break;
            case CONTINUATION:
                out.add(new ContinuationWebSocketFrame(payload));
                break;
            case PING:
                out.add(new PingWebSocketFrame(payload));
                break;
            case PONG:
                out.add(new PongWebSocketFrame(payload));
                break;
            default:
                throw new IllegalStateException("Unsupported websocket msg:" + msg);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {

    }

    public static final class MyWebSocketFrame {
        public enum FrameType {
            BINARY,CLOSE,PING,PONG,TEXT,CONTINUATION
        }
        private final FrameType type;
        private final ByteBuf data;
        public MyWebSocketFrame(FrameType type, ByteBuf data){
            this.type = type;
            this.data = data;
        }

        public FrameType getType(){
            return type;
        }

        public ByteBuf getData(){
            return data;
        }
    }
}
