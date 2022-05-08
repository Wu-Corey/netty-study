package com.example.nettysutdy.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 2022/5/8
 */
@Slf4j
public class HttpServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 线程队列连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持活动连接装填
                    .childHandler(new HttpInitializer());

            log.info("服务端ready...");

            // 启动服务器并绑定端口
            ChannelFuture future = bootstrap.bind(8081).sync();

            // 监听绑定事件
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("绑定端口成功");
                    } else {
                        log.info("绑定端口失败");
                    }
                }
            });

            // 对关闭通道监听
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("e", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
