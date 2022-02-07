package com.icerabbit.netty_in_action.part2.echo_cs.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/2/7 16:26
 **/
@Slf4j
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * Called when a message is received from the server
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
      log.info("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * Called after the connection to the server is established
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
