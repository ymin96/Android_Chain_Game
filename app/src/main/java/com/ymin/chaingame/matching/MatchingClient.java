package com.ymin.chaingame.matching;

import android.app.Activity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class MatchingClient {
    SocketChannel socketChannel;
    Activity activity = null;

    public void startClient() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    socketChannel = SocketChannel.open();
                    socketChannel.configureBlocking(true);
                    socketChannel.connect(new InetSocketAddress("localhost", 5002));

                } catch (IOException e) {
                    if(socketChannel.isOpen()){stopClient();}
                    return;
                }
                receive();
            }
        };
        thread.start();
    }

    void stopClient() {
        try{
            if(socketChannel != null && socketChannel.isOpen())
                socketChannel.close();
        }catch (IOException e){}
    }

    void receive(){
        while(true){
            try{
                ByteBuffer byteBuffer = ByteBuffer.allocate(100);

                // 서버가 비정상적으로 종료했을 경우 IOException 발생
                int readByteCount = socketChannel.read(byteBuffer);

                // 서버가 정상적으로 Socket 의 close()를 호출했을 경우
                if(readByteCount == -1)
                    throw new IOException();

                byteBuffer.flip();
                Charset charset = Charset.forName("UTF-8");
                String data = charset.decode(byteBuffer).toString();

            } catch (IOException e) {
                stopClient();
                break;
            }
        }
    }
}
