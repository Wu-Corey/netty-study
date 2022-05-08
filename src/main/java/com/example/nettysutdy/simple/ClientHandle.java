package com.example.nettysutdy.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 2022/5/8
 */
@Slf4j
public class ClientHandle extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪会触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端ctx:{}",ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server...", CharsetUtil.UTF_8));
    }

    /**
     * 通道有读事件会触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("服务端发送的消息是:{}",buf.toString(CharsetUtil.UTF_8));
        log.info("服务端地址:{}",ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught error",cause);
        ctx.close();
    }
}
