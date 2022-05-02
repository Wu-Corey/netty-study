package com.example.nettysutdy.io.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2022/5/1
 */
@Slf4j
public class NioServer {

    public static void main(String[] args) {

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(6666);
            log.info("服务端监听中...");

            ExecutorService threadPool = Executors.newCachedThreadPool();
            while (true){
                Socket socket = serverSocket.accept();
                log.info("客户端建立连接 socket:{}",socket);
                threadPool.execute(()->{
                    handleConnection(socket);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket){
        try {
            log.info("客户端准备读...");
            InputStream is = socket.getInputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while (true){
                try {
                    if ((len = is.read(buffer)) == -1) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info(new String(buffer,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
