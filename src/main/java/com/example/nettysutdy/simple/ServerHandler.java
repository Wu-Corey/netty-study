package com.example.nettysutdy.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 2022/5/8
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(10000);
                log.info("睡眠了10s");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().schedule(()->{
            try {
                Thread.sleep(10000);
                log.info("睡眠了10s");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },5, TimeUnit.SECONDS);

//        log.info("ctx:{}",ctx);
//        ByteBuf byteBuf = (ByteBuf) msg;
//
//        log.info("客户端发送的消息:{}",byteBuf.toString(CharsetUtil.UTF_8));
//        log.info("客户端地址:{}",ctx.channel().remoteAddress());

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端...",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught error",cause);
        ctx.close();
    }
}
