package com.example.nettysutdy.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 2022/5/8
 */
@Slf4j
public class Client {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new ClientHandle());
                        }
                    });


            log.info("客户端ready...");

            // 启动客户端并连接
            ChannelFuture future = bootstrap.connect("127.0.0.1",6666).sync();

            // 监听关闭通道
            future.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("e",e);
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
