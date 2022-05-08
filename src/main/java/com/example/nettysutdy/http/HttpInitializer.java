package com.example.nettysutdy.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 2022/5/8
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("myHttpServerCodec",new HttpServerCodec()).addLast("myHttpServerHandler",new HttpServerHandler());
    }
}
