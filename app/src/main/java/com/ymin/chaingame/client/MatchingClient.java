package com.ymin.chaingame.client;

import android.app.Activity;
import android.util.Log;

import com.ymin.chaingame.dialog.CheckTypesTask;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class MatchingClient {
    static final private String TAG = "MatchingClient";

    SocketChannel socketChannel;
    Activity activity = null;
    CheckTypesTask task;
    ActionCreator actionCreator = new ActionCreator();

    public MatchingClient(CheckTypesTask task){
        this.task = task;
    }

    public String startClient() {
        try{
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            socketChannel.connect(new InetSocketAddress("10.0.2.2", 5002));
            Log.d(TAG, "startClient: 매칭 서버 연결 성공");
        } catch (IOException e) {
            if(socketChannel.isOpen()){stopClient();}
            return null;
        }
        return receive();

    }

    void stopClient() {
        try{
            if(socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
                Log.d(TAG, "stopClient: 매칭 서버 연결 해제");
            }
        }catch (IOException e){}
    }

    String receive(){
        while(!task.isCancelled()){
            try{
                ByteBuffer byteBuffer = ByteBuffer.allocate(100);

                // 서버가 비정상적으로 종료했을 경우 IOException 발생
                int readByteCount = socketChannel.read(byteBuffer);

                // 서버가 정상적으로 Socket 의 close()를 호출했을 경우
                if(readByteCount == -1) {
                    task.cancel(true);
                    throw new IOException();
                }

                byteBuffer.flip();
                Charset charset = Charset.forName("UTF-8");
                String data = charset.decode(byteBuffer).toString();

                // 데이터를 받아 처리한다.
                JSONParser jsonParser = new JSONParser();
                JSONObject result = (JSONObject)jsonParser.parse(data);
                String type = (String)result.get("type");
                JSONObject payload = (JSONObject) result.get("payload");
                if(type.equals("CONNECT_SUCCESS")){
                    String uuid = (String) payload.get("uuid");
                    return uuid;
                }

            } catch (IOException | ParseException e) {
                stopClient();
                return null;
            }
        }

        return null;
    }

    public void send(final String data){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    Charset charset = Charset.forName("UTF-8");
                    ByteBuffer byteBuffer = charset.encode(data);
                    socketChannel.write(byteBuffer);
                }catch (Exception e){
                    stopClient();
                }
            }
        };
        thread.start();
    }
}
